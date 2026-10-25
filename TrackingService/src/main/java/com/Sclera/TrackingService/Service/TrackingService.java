package com.Sclera.TrackingService.Service;



import com.Sclera.TrackingService.DTO.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrackingService {
    TrackingResponse createTracking(CreateTrackingRequest request);
    TrackingResponse updateTracking(String trackingNumber, UpdateTrackingRequest request);
    TrackingResponse getTracking(String trackingNumber);
    Page<TrackingHistoryResponse> getHistory(String trackingNumber, Pageable pageable);
}
