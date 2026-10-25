package com.Sclera.TrackingService.Service;
import com.Sclera.TrackingService.Client.PackageServiceClient;
import com.Sclera.TrackingService.Config.RabbitMQConfig;
import com.Sclera.TrackingService.DTO.*;
import com.Sclera.TrackingService.Entity.Tracking;
import com.Sclera.TrackingService.Entity.TrackingHistory;
import com.Sclera.TrackingService.Entity.TrackingStatus;
import com.Sclera.TrackingService.Event.TrackingUpdatedEvent;
import com.Sclera.TrackingService.Exception.TrackingAlreadyExistsException;
import com.Sclera.TrackingService.Exception.TrackingNotFoundException;
import com.Sclera.TrackingService.Repository.TrackingHistoryRepository;
import com.Sclera.TrackingService.Repository.TrackingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TrackingServiceImpl implements TrackingService {

    private static final String CACHE_KEY_PREFIX = "tracking:";
    private static final long CACHE_TTL_MINUTES = 10;

    private final TrackingRepository trackingRepository;
    private final TrackingHistoryRepository trackingHistoryRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final PackageServiceClient packageServiceClient;
    @Autowired
    public RabbitTemplate rabbitTemplate;
    private static final Logger log = LoggerFactory.getLogger(TrackingServiceImpl.class);


    public TrackingServiceImpl(TrackingRepository trackingRepository,
                               TrackingHistoryRepository trackingHistoryRepository,
                               RedisTemplate<String, Object> redisTemplate,
                               PackageServiceClient packageServiceClient) {
        this.trackingRepository = trackingRepository;
        this.trackingHistoryRepository = trackingHistoryRepository;
        this.redisTemplate = redisTemplate;
        this.packageServiceClient = packageServiceClient;
    }

    public TrackingResponse createTracking(CreateTrackingRequest request) {
        trackingRepository.findByTrackingNumber(request.getTrackingNumber())
                .ifPresent(t -> {
                    throw new TrackingAlreadyExistsException(request.getTrackingNumber());
                });

        Tracking tracking = new Tracking(
                UUID.randomUUID().toString(),
                request.getTrackingNumber(),
                request.getCurrentLocation(),
                TrackingStatus.CREATED
        );

        Tracking saved = trackingRepository.save(tracking);
        return toResponse(saved);
    }

    public TrackingResponse updateTracking(String trackingNumber, UpdateTrackingRequest request) {
            packageServiceClient.verifyPackageExists(trackingNumber);

            log.info("Verifying package exists");

            Tracking tracking = trackingRepository.findByTrackingNumber(trackingNumber)
                    .orElseThrow(() -> new TrackingNotFoundException(trackingNumber));

            tracking.setCurrentLocation(request.getLocation());
            tracking.setStatus(request.getStatus());
            trackingRepository.save(tracking);

            TrackingHistory history = new TrackingHistory(
                    UUID.randomUUID().toString(),
                    trackingNumber,
                    request.getLocation(),
                    request.getStatus(),
                    request.getRemarks()
            );
            trackingHistoryRepository.save(history);

            redisTemplate.delete(CACHE_KEY_PREFIX + trackingNumber);

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.TRACKING_UPDATED_ROUTING_KEY,
                    new TrackingUpdatedEvent(trackingNumber, request.getStatus().name())
            );
            log.info("Sent tracking.updated event");

            return toResponse(tracking);
        }



    public TrackingResponse getTracking(String trackingNumber) {
        String cacheKey = CACHE_KEY_PREFIX + trackingNumber;

        TrackingResponse cached = (TrackingResponse) redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return cached;
        }

        Tracking tracking = trackingRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new TrackingNotFoundException(trackingNumber));
        TrackingResponse response = toResponse(tracking);

        redisTemplate.opsForValue().set(cacheKey, response, CACHE_TTL_MINUTES, TimeUnit.MINUTES);
        return response;
    }

    public Page<TrackingHistoryResponse> getHistory(String trackingNumber, Pageable pageable) {
        return trackingHistoryRepository.findByTrackingNumber(trackingNumber, pageable)
                .map(h -> new TrackingHistoryResponse(
                        h.getLocation(), h.getStatus(), h.getRemarks(), h.getTimestamp()));
    }

    private TrackingResponse toResponse(Tracking tracking) {
        return new TrackingResponse(
                tracking.getTrackingNumber(),
                tracking.getCurrentLocation(),
                tracking.getStatus(),
                tracking.getLastUpdated()
        );
    }
}