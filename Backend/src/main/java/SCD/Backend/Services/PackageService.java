package SCD.Backend.Services;

import SCD.Backend.DTOs.PackageDTO;
import SCD.Backend.Models.APIMessage;
import SCD.Backend.Models.Package;
import SCD.Backend.Repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    public ResponseEntity<?> getDeliveryByID(Integer id) {
        return ResponseEntity.ok(packageRepository.findById(id));
    }

    public ResponseEntity<?> deletePackage(Integer id) {
        Optional<Package> packageOptional = packageRepository.findById(id);
        if(packageOptional.isPresent()) {
            packageRepository.delete(packageOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Pachetul a fost sters cu succes!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Pachetul nu a fost gasit!"));
    }

    public ResponseEntity<?> editPackage(Integer id, PackageDTO editPackage) {
        Optional<Package> packageOptional = packageRepository.findById(id);
        if(packageOptional.isPresent()) {
            packageOptional.get().setDeliveryAddress(editPackage.getDeliveryAddress());
            packageRepository.save(packageOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Detaliile pachetului au fost editate cu succes!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new APIMessage(HttpStatus.OK, "Pachetul nu a fost gasit!"));
    }
    @Transactional
    public ResponseEntity<?> setPackageStatus(Package pk) {
        packageRepository.save(pk);
        return ResponseEntity.ok(new APIMessage(HttpStatus.ACCEPTED, "Statusul a fost modificat cu succes!"));
    }
}
