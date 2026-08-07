package com.Sclera.TrackingService.DTO;

import com.Sclera.TrackingService.Entity.TrackingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateTrackingRequest {
    @NotBlank(message = "location is required")
    private String location;

    @NotNull(message = "status is required")
    private TrackingStatus status;

    private String remarks;

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public TrackingStatus getStatus() { return status; }
    public void setStatus(TrackingStatus status) { this.status = status; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}