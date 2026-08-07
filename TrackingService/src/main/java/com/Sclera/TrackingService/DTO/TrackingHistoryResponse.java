package com.Sclera.TrackingService.DTO;

import com.Sclera.TrackingService.Entity.TrackingStatus;
import java.time.LocalDateTime;

public class TrackingHistoryResponse {
    private String location;
    private TrackingStatus status;
    private String remarks;
    private LocalDateTime timestamp;

    public TrackingHistoryResponse(String location, TrackingStatus status,
                                   String remarks, LocalDateTime timestamp) {
        this.location = location;
        this.status = status;
        this.remarks = remarks;
        this.timestamp = timestamp;
    }

    public String getLocation() { return location; }
    public TrackingStatus getStatus() { return status; }
    public String getRemarks() { return remarks; }
    public LocalDateTime getTimestamp() { return timestamp; }
}