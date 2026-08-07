package com.Sclera.PackageService.Repository;


import com.Sclera.PackageService.Entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, String> {
    // findAll(Pageable) is inherited — no need to write pagination logic yourself
}
