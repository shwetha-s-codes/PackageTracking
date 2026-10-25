package com.Sclera.TrackingService.Event;

import java.io.Serializable;

public class PackageCreatedEvent implements Serializable {
    private String trackingNumber;
    private String sourceCity;
    private String destinationCity;

    public PackageCreatedEvent() {
    }

    public PackageCreatedEvent(String trackingNumber, String sourceCity, String destinationCity) {
        this.trackingNumber = trackingNumber;
        this.sourceCity = sourceCity;
        this.destinationCity = destinationCity;
    }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public String getSourceCity() { return sourceCity; }
    public void setSourceCity(String sourceCity) { this.sourceCity = sourceCity; }
    public String getDestinationCity() { return destinationCity; }
    public void setDestinationCity(String destinationCity) { this.destinationCity = destinationCity; }
}