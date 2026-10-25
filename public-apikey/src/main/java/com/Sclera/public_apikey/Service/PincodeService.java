package com.Sclera.public_apikey.Service;

import com.Sclera.public_apikey.DTO.PincodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class PincodeService {

    private final WebClient webClient;
    public PincodeResponse pincodeResponse;
    private static final Logger log = LoggerFactory.getLogger(PincodeService.class);

    public PincodeService(WebClient webClient) {
        this.webClient = webClient;
    }

    public ResponseEntity<?> getCity(String pincode) {

        List<PincodeResponse> response = webClient.get()
                .uri("/pincode/{pincode}", pincode)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PincodeResponse>>() {})
                .block();

        log.info("Response from pincode service {}", response.get(0).getStatus());
        log.info("Response from pincode service {}", response.get(0).getMessage());

        if (response == null || response.isEmpty()) {
            throw new RuntimeException("Invalid response");
        }

        pincodeResponse = response.get(0);

        pincodeResponse.setMessage(pincodeResponse.getMessage());
        pincodeResponse.setStatus(pincodeResponse.getStatus());
        pincodeResponse.setPostOffice(pincodeResponse.getPostOffice());

        if ("Error".equalsIgnoreCase(pincodeResponse.getStatus())
                || pincodeResponse.getPostOffice() == null
                || pincodeResponse.getPostOffice().isEmpty()) {

            throw new RuntimeException("Invalid pincode");
        }

        return  new ResponseEntity<>(pincodeResponse.getPostOffice().get(0).getName(), HttpStatus.OK);
    }
}
