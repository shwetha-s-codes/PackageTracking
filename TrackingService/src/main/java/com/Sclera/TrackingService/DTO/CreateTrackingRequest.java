package com.Sclera.TrackingService.DTO;
import jakarta.validation.constraints.NotBlank;

public class CreateTrackingRequest {
    @NotBlank(message = "trackingNumber is required")
    private String trackingNumber;

    @NotBlank(message = "currentLocation is required")
    private String currentLocation;

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }
}
