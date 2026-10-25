package com.Sclera.TrackingService.Messaging;

import com.Sclera.TrackingService.Config.RabbitMQConfig;
import com.Sclera.TrackingService.DTO.CreateTrackingRequest;
import com.Sclera.TrackingService.Event.PackageCreatedEvent;
import com.Sclera.TrackingService.Exception.TrackingAlreadyExistsException;
import com.Sclera.TrackingService.Service.TrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PackageCreatedlistener {

    private static final Logger log = LoggerFactory.getLogger(PackageCreatedlistener.class);

    private final TrackingService trackingService;

    public PackageCreatedlistener(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @RabbitListener(queues = RabbitMQConfig.PACKAGE_CREATED_QUEUE)
    public void handlePackageCreated(PackageCreatedEvent event) {
        log.info("Received package.created event for {}", event.getTrackingNumber());

        CreateTrackingRequest request = new CreateTrackingRequest();
        request.setTrackingNumber(event.getTrackingNumber());
        request.setCurrentLocation(event.getSourceCity());

        try {
            trackingService.createTracking(request);
            log.info("Created tracking record for {}", event.getTrackingNumber());
        } catch (TrackingAlreadyExistsException ex) {

            log.warn("Tracking record already exists for {} - skipping duplicate event", event.getTrackingNumber());
        }
    }
}