package com.Sclera.PackageService.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "package")
public class Package {

    @Id
    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "sender_name", nullable = false)
    private String senderName;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Column(name = "source_pincode", nullable = false)
    private String sourcePincode;

    @Column(name = "destination_pincode", nullable = false)
    private String destinationPincode;

    @Column(name = "source_city")
    private String sourceCity;

    @Column(name = "destination_city")
    private String destinationCity;

    @Column(nullable = false)
    private Double weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PackageStatus status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    protected Package() {
        // JPA needs a no-arg constructor
    }

    public Package(String trackingNumber, String senderName, String receiverName,
                   String sourcePincode, String destinationPincode,
                   Double weight, PackageStatus status) {
        this.trackingNumber = trackingNumber;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.sourcePincode = sourcePincode;
        this.destinationPincode = destinationPincode;
        this.weight = weight;
        this.status = status;
    }



    public String getTrackingNumber() { return trackingNumber; }
    public String getSenderName() { return senderName; }
    public String getReceiverName() { return receiverName; }
    public String getSourcePincode() { return sourcePincode; }
    public String getDestinationPincode() { return destinationPincode; }
    public String getSourceCity() { return sourceCity; }
    public void setSourceCity(String sourceCity) { this.sourceCity = sourceCity; }
    public String getDestinationCity() { return destinationCity; }
    public void setDestinationCity(String destinationCity) { this.destinationCity = destinationCity; }
    public Double getWeight() { return weight; }
    public PackageStatus getStatus() { return status; }
    public void setStatus(PackageStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}