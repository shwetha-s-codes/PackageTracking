package com.Sclera.PackageService.Service;
import com.Sclera.PackageService.Client.PincodeClient;
import com.Sclera.PackageService.Config.RabbitMQConfig;
import com.Sclera.PackageService.Event.PackageCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.Sclera.PackageService.Client.PincodeClient;
import com.Sclera.PackageService.DTO.PackageRequest;
import com.Sclera.PackageService.DTO.PackageResponse;
import com.Sclera.PackageService.Entity.Package;
import com.Sclera.PackageService.Entity.PackageStatus;
import com.Sclera.PackageService.Exception.PackageNotFoundException;
import com.Sclera.PackageService.Repository.PackageRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PackageServiceImpl implements PackageService {

    private static final String CACHE_KEY_PREFIX = "package:";
    private static final long CACHE_TTL_MINUTES = 10;

    private final PackageRepository packageRepository;
    //private final PincodeClient pincodeClient;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final Logger log = LoggerFactory.getLogger(PackageServiceImpl.class);
   @Autowired
    public RabbitTemplate rabbitTemplate;

    public PackageServiceImpl(PackageRepository packageRepository,
                              PincodeClient pincodeClient,
                              RedisTemplate<String, Object> redisTemplate) {
        this.packageRepository = packageRepository;
        //this.pincodeClient = pincodeClient;
        this.redisTemplate = redisTemplate;
    }

    public PackageResponse createPackage(PackageRequest request) {
        //String sourceCity = pincodeClient.resolveCity(request.getSourcePincode());
        //String destinationCity = pincodeClient.resolveCity(request.getDestinationPincode());


        String trackingNumber = UUID.randomUUID().toString();

        Package pkg = new Package(
                trackingNumber,
                request.getSenderName(),
                request.getReceiverName(),
                request.getSourcePincode(),
                request.getDestinationPincode(),
                request.getWeight(),
                PackageStatus.CREATED
        );
        //pkg.setSourceCity(sourceCity);
        //pkg.setDestinationCity(destinationCity);

        Package saved = packageRepository.save(pkg);
        log.info("Package Saved");


        //Instead of directly sending the event, we are sending it to a queue

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.PACKAGE_CREATED_ROUTING_KEY,
                new PackageCreatedEvent(saved.getTrackingNumber(), saved.getSourceCity(), saved.getDestinationCity())
        );
        log.info("Sent package.created event");
        return toResponse(saved);
    }

    public PackageResponse getPackage(String trackingNumber) {
        String cacheKey = CACHE_KEY_PREFIX + trackingNumber;

        PackageResponse cached = (PackageResponse) redisTemplate.opsForValue().get(cacheKey);
        log.info("Cached Info is ",cached);
        if (cached != null) {
            log.info("Info retreived from Cache");
            return cached; // cache hit
        }
        log.info("Info Retrieving from DB");

        Package pkg = packageRepository.findById(trackingNumber)
                .orElseThrow(() -> new PackageNotFoundException(trackingNumber));
        PackageResponse response = toResponse(pkg);

        //TLL
        redisTemplate.opsForValue().set(cacheKey, response, CACHE_TTL_MINUTES, TimeUnit.MINUTES);
        return response;
    }

    public Page<PackageResponse> listPackages(Pageable pageable) {
        return packageRepository.findAll(pageable).map(this::toResponse);
    }

    private PackageResponse toResponse(Package pkg) {
        return new PackageResponse(
                pkg.getTrackingNumber(),
                pkg.getSenderName(),
                pkg.getReceiverName(),
                pkg.getSourcePincode(),
                pkg.getDestinationPincode(),
                pkg.getSourceCity(),
                pkg.getDestinationCity(),
                pkg.getWeight(),
                pkg.getStatus(),
                pkg.getCreatedAt()
        );
    }
}