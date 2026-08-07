package com.Sclera.TrackingService.Repository;
import com.Sclera.TrackingService.Entity.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking, String> {
    Optional<Tracking> findByTrackingNumber(String trackingNumber);
}