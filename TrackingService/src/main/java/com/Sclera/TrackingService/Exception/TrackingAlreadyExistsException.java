package com.Sclera.TrackingService.Exception;

public class TrackingAlreadyExistsException extends RuntimeException {
    public TrackingAlreadyExistsException(String trackingNumber) {
        super("Tracking record already exists for tracking number: " + trackingNumber);
    }
}