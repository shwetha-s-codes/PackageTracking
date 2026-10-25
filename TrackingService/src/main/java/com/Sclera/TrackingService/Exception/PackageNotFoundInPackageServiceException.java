package com.Sclera.TrackingService.Exception;

public class PackageNotFoundInPackageServiceException extends RuntimeException {
    public PackageNotFoundInPackageServiceException(String trackingNumber) {
        super("No package found in Package Service for tracking number: " + trackingNumber);
    }
}