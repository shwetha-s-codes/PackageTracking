package com.Sclera.TrackingService.DTO;

import com.Sclera.TrackingService.Entity.TrackingStatus;
import java.time.LocalDateTime;

public class TrackingResponse {
    private String trackingNumber;
    private String currentLocation;
    private TrackingStatus status;
    private LocalDateTime lastUpdated;

    public TrackingResponse(String trackingNumber, String currentLocation,
                            TrackingStatus status, LocalDateTime lastUpdated) {
        this.trackingNumber = trackingNumber;
        this.currentLocation = currentLocation;
        this.status = status;
        this.lastUpdated = lastUpdated;
    }

    public String getTrackingNumber() { return trackingNumber; }
    public String getCurrentLocation() { return currentLocation; }
    public TrackingStatus getStatus() { return status; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
}