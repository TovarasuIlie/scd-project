package SCD.Backend.Controllers;

import SCD.Backend.DTOs.NewPackageDTO;
import SCD.Backend.Services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/Package")
@RestController
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping("get-all-deliveries")
    public ResponseEntity<?> getAllDeliveries() {
        return packageService.getAllDeliveries();
    }

    @PostMapping("create-delivery")
    public ResponseEntity<?> createDelivery(@RequestBody NewPackageDTO newPackageDTO) {
        return packageService.createNewDelivery(newPackageDTO);
    }
}
