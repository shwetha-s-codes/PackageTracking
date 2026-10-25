package com.Sclera.public_apikey.Controller;

import com.Sclera.public_apikey.DTO.PincodeResponse;
import com.Sclera.public_apikey.Service.PincodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PincodeController {

    private final PincodeService pincodeService;

    private static final Logger log = LoggerFactory.getLogger(PincodeController.class);

    public PincodeController(PincodeService pincodeService) {
        this.pincodeService = pincodeService;
    }

    @GetMapping("/city/{pincode}")
    public ResponseEntity<?> getCity(@PathVariable String pincode) {

        log.info("Received request for pincode {}", pincode);

        return new ResponseEntity<>(pincodeService.getCity(pincode), HttpStatus.OK);
    }
}