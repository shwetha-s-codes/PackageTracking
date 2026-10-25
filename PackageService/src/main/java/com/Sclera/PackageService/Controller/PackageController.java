package com.Sclera.PackageService.Controller;
import com.Sclera.PackageService.DTO.PackageRequest;
import com.Sclera.PackageService.DTO.PackageResponse;
import com.Sclera.PackageService.Exception.InvalidPincodeException;
import com.Sclera.PackageService.Service.PackageService;
import com.Sclera.PackageService.Service.PackageServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/packages")
public class PackageController {

    private final PackageService packageService;
    private static final Logger log = LoggerFactory.getLogger(PackageServiceImpl.class);

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping
    public ResponseEntity<PackageResponse> createPackage(@Valid @RequestBody PackageRequest request) throws InvalidPincodeException {
        PackageResponse response = packageService.createPackage(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{trackingNumber}")
    public ResponseEntity<PackageResponse> getPackage(@PathVariable String trackingNumber) {
        log.info("Hit the get end point");
        return ResponseEntity.ok(packageService.getPackage(trackingNumber));
    }

    @GetMapping
    public ResponseEntity<Page<PackageResponse>> listPackages(Pageable pageable) {
        return ResponseEntity.ok(packageService.listPackages(pageable));
    }
}
