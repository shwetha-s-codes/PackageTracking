package com.Sclera.TrackingService.Exception;

public class TrackingNotFoundException extends RuntimeException {
    public TrackingNotFoundException(String trackingNumber) {
        super("Tracking record not found for tracking number: " + trackingNumber);
    }
}