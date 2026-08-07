package com.Sclera.PackageService.Service;

import com.Sclera.PackageService.DTO.PackageRequest;
import com.Sclera.PackageService.DTO.PackageResponse;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PackageService {
    PackageResponse createPackage(@Valid PackageRequest request);

    @Nullable PackageResponse getPackage(String trackingNumber);

    @Nullable Page<PackageResponse> listPackages(Pageable pageable);
}
