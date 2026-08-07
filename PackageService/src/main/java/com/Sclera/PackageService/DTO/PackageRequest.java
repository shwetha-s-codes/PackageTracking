package com.Sclera.PackageService.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PackageRequest {

    @NotBlank(message = "senderName is required")
    private String senderName;

    @NotBlank(message = "receiverName is required")
    private String receiverName;

    @NotBlank(message = "sourcePincode is required")
    private String sourcePincode;

    @NotBlank(message = "destinationPincode is required")
    private String destinationPincode;

    @NotNull(message = "weight is required")
    @Positive(message = "weight must be positive")
    private Double weight;

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }
    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }
    public String getSourcePincode() { return sourcePincode; }
    public void setSourcePincode(String sourcePincode) { this.sourcePincode = sourcePincode; }
    public String getDestinationPincode() { return destinationPincode; }
    public void setDestinationPincode(String destinationPincode) { this.destinationPincode = destinationPincode; }
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
}