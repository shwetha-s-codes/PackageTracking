package com.Sclera.TrackingService.Controller;

import com.Sclera.TrackingService.DTO.*;
import com.Sclera.TrackingService.Service.TrackingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tracking")
public class TrackingController {

    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @PostMapping // TEMPORARY - remove once RabbitMQ auto-creates tracking records (Step 6)
    public ResponseEntity<TrackingResponse> createTracking(@Valid @RequestBody CreateTrackingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trackingService.createTracking(request));
    }

    @PutMapping("/{trackingNumber}")
    public ResponseEntity<TrackingResponse> updateTracking(
            @PathVariable String trackingNumber,
            @Valid @RequestBody UpdateTrackingRequest request) {
        return ResponseEntity.ok(trackingService.updateTracking(trackingNumber, request));
    }

    @GetMapping("/{trackingNumber}")
    public ResponseEntity<TrackingResponse> getTracking(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(trackingService.getTracking(trackingNumber));
    }

    @GetMapping("/{trackingNumber}/history")
    public ResponseEntity<Page<TrackingHistoryResponse>> getHistory(
            @PathVariable String trackingNumber, Pageable pageable) {
        return ResponseEntity.ok(trackingService.getHistory(trackingNumber, pageable));
    }
}