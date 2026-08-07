package com.Sclera.PackageService.DTO;

import com.Sclera.PackageService.Entity.PackageStatus;
import java.time.LocalDateTime;

public class PackageResponse {
    private String trackingNumber;
    private String senderName;
    private String receiverName;
    private String sourcePincode;
    private String destinationPincode;
    private String sourceCity;
    private String destinationCity;
    private Double weight;
    private PackageStatus status;
    private LocalDateTime createdAt;

    public PackageResponse(String trackingNumber, String senderName, String receiverName,
                           String sourcePincode, String destinationPincode,
                           String sourceCity, String destinationCity,
                           Double weight, PackageStatus status, LocalDateTime createdAt) {
        this.trackingNumber = trackingNumber;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.sourcePincode = sourcePincode;
        this.destinationPincode = destinationPincode;
        this.sourceCity = sourceCity;
        this.destinationCity = destinationCity;
        this.weight = weight;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getTrackingNumber() { return trackingNumber; }
    public String getSenderName() { return senderName; }
    public String getReceiverName() { return receiverName; }
    public String getSourcePincode() { return sourcePincode; }
    public String getDestinationPincode() { return destinationPincode; }
    public String getSourceCity() { return sourceCity; }
    public String getDestinationCity() { return destinationCity; }
    public Double getWeight() { return weight; }
    public PackageStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}