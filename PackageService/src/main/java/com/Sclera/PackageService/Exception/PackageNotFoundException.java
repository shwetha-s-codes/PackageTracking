package com.Sclera.PackageService.Exception;

public class PackageNotFoundException extends RuntimeException{
    public PackageNotFoundException(String trackingNumber) {
        super("Package not found with tracking number: " + trackingNumber);
    }
}
