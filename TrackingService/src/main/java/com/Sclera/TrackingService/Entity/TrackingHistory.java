package com.Sclera.TrackingService.Entity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "tracking_history")
public class TrackingHistory {

    @Id
    @Column(name="id")
    private String id;

    @Column(name = "tracking_number", nullable = false)
    private String trackingNumber;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrackingStatus status;

    private String remarks;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime timestamp;

    protected TrackingHistory() {
    }

    public TrackingHistory(String id,String trackingNumber, String location, TrackingStatus status, String remarks) {
        this.id=id;
        this.trackingNumber = trackingNumber;
        this.location = location;
        this.status = status;
        this.remarks = remarks;
    }

    public String getId() { return id; }
    public String getTrackingNumber() { return trackingNumber; }
    public String getLocation() { return location; }
    public TrackingStatus getStatus() { return status; }
    public String getRemarks() { return remarks; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
