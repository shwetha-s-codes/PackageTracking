package com.Sclera.PackageService.Exception;

public class InvalidPincodeException extends Throwable {
    public InvalidPincodeException(String pincode) {
        super("Invalid pincode: " + pincode);
    }
}
