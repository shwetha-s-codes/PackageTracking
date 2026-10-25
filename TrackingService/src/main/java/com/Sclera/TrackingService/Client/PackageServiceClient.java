package com.Sclera.TrackingService.Client;

import com.Sclera.TrackingService.Exception.PackageNotFoundInPackageServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class PackageServiceClient {

    private final WebClient webClient;

    public PackageServiceClient(WebClient packageServiceWebClient) {
        this.webClient = packageServiceWebClient;
    }

    public boolean packageExists(String trackingNumber) {
        try {
            webClient.get()
                    .uri("/packages/{trackingNumber}", trackingNumber)
                    .retrieve()
                    .toBodilessEntity()
                    .block(); // blocking call - deliberate, see note below
            return true;
        } catch (WebClientResponseException.NotFound ex) {
            return false;
        }
    }

    public void verifyPackageExists(String trackingNumber) {
        if (!packageExists(trackingNumber)) {
            throw new PackageNotFoundInPackageServiceException(trackingNumber);
        }
    }
}