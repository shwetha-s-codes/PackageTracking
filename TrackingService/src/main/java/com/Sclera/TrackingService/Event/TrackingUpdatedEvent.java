package com.Sclera.TrackingService.Event; // and matching com.Sclera.PackageService.Event

import java.io.Serializable;

public class TrackingUpdatedEvent implements Serializable {
    private String trackingNumber;
    private String trackingStatus;



    public TrackingUpdatedEvent(String trackingNumber, String trackingStatus) {
        this.trackingNumber = trackingNumber;
        this.trackingStatus = trackingStatus;
    }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public String getTrackingStatus() { return trackingStatus; }
    public void setTrackingStatus(String trackingStatus) { this.trackingStatus = trackingStatus; }
}
