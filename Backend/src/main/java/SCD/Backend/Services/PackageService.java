package SCD.Backend.Services;

import SCD.Backend.DTOs.NewPackageDTO;
import SCD.Backend.DTOs.PackageStatus;
import SCD.Backend.Models.APIMessage;
import SCD.Backend.Models.Package;
import SCD.Backend.Repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    public ResponseEntity<?> getAllDeliveries() {
        return ResponseEntity.ok(packageRepository.findAll());
    }

    public ResponseEntity<?> createNewDelivery(NewPackageDTO newPackage) {
        Package p = new Package();
        p.setCourier(null);
        p.setDeliveryAddress(newPackage.getDeliveryAddress());
        p.setPayOnDelivery(false);
        p.setStatus(PackageStatus.NEW);
        p.setCreatedOn(LocalDateTime.now());
        packageRepository.save(p);

        return ResponseEntity.status(HttpStatus.CREATED).body(new APIMessage(HttpStatus.CREATED, "Comanda inserata!"));
    }

    public ResponseEntity<?> getDeliveryByID(Integer id) {
        return ResponseEntity.ok(packageRepository.findById(id));
    }
}
