package com.Sclera.TrackingService.Repository;

import com.Sclera.TrackingService.Entity.TrackingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingHistoryRepository extends JpaRepository<TrackingHistory, String> {
    Page<TrackingHistory> findByTrackingNumber(String trackingNumber, Pageable pageable);
}
