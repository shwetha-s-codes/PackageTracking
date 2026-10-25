package com.Sclera.PackageService.Client;

import com.Sclera.PackageService.DTO.External.PincodeApiResponse;
import com.Sclera.PackageService.Exception.InvalidPincodeException;
import com.Sclera.PackageService.Exception.PincodeServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;

@Component
public class PincodeClient {

    private static final int MAX_ATTEMPTS = 3;

    private final RestClient restClient;
    private static final Logger log = LoggerFactory.getLogger(PincodeClient.class);

    public PincodeClient(RestClient.Builder builder) {
        HttpClient jdkClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(jdkClient);
        requestFactory.setReadTimeout(Duration.ofSeconds(5));

        this.restClient = builder
                .baseUrl("https://api.postalpincode.in")
                .requestFactory(requestFactory)
                .build();
    }

    public String resolveCity(String pincode) throws PincodeServiceUnavailableException, InvalidPincodeException {
        PincodeApiResponse[] response = null;
        Exception lastError = null;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                log.info("Attempting to resolve city for pincode {}", pincode);
                response = restClient.get()
                        .uri("/pincode/{pincode}", pincode)
                        .retrieve()
                        .body(PincodeApiResponse[].class);
                log.info("Successfully resolved city for pincode {}", response[0].getPostOffice().get(0).getDistrict());
                break; // success
            } catch (Exception ex) {
                lastError = ex;
                if (attempt < MAX_ATTEMPTS) {
                    try {
                        Thread.sleep(300L * attempt); // simple backoff: 300ms, 600ms
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }

        if (response == null) {
            throw new PincodeServiceUnavailableException(
                    "Failed to reach pincode validation service", lastError);
        }

        if (response.length == 0 || !"Success".equalsIgnoreCase(response[0].getStatus())) {
            throw new InvalidPincodeException(pincode);
        }

        List<PincodeApiResponse.PostOffice> offices = response[0].getPostOffice();
        if (offices == null || offices.isEmpty()) {
            throw new InvalidPincodeException(pincode);
        }

        return offices.get(0).getDistrict();
    }
}