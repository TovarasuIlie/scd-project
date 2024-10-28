package SCD.Backend.Controllers;

import SCD.Backend.DTOs.PackageDTO;
import SCD.Backend.Services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/Package")
@RestController
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping("get-package-by-id/{id}")
    public ResponseEntity<?> getDeliveryById(@PathVariable Integer id) {
        return packageService.getDeliveryByID(id);
    }

    @PutMapping("edit-package/{id}")
    public ResponseEntity<?> editPackageDetails(@PathVariable Integer id, @RequestBody PackageDTO editPackage) {
        return packageService.editPackage(id, editPackage);
    }

    @DeleteMapping("delete-package/{id}")
    public ResponseEntity<?> deletePackage(@PathVariable Integer id) {
        return packageService.deletePackage(id);
    }

}
