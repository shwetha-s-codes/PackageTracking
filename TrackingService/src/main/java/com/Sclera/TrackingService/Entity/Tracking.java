package com.Sclera.TrackingService.Entity;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "tracking")
public class Tracking {

    @Id
    @Column(name = "id")
    private String id;


    @Column(name = "tracking_number", nullable = false, unique = true)
    private String trackingNumber;

    @Column(name = "current_location")
    private String currentLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrackingStatus status;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

   protected Tracking(){}

    public Tracking(String id,String trackingNumber, String currentLocation, TrackingStatus status) {
       this.id=id;
       this.trackingNumber = trackingNumber;
        this.currentLocation = currentLocation;
        this.status = status;
    }

    public String getId() { return id; }
    public String getTrackingNumber() { return trackingNumber; }
    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }
    public TrackingStatus getStatus() { return status; }
    public void setStatus(TrackingStatus status) { this.status = status; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
}
