package SCD.Backend.Services;

import SCD.Backend.DTOs.PackageDTO;
import SCD.Backend.DTOs.DeliveryDTO;
import SCD.Backend.Models.*;
import SCD.Backend.Models.Package;
import SCD.Backend.Repositories.CourierRepository;
import SCD.Backend.Repositories.DeliveryRepository;
import SCD.Backend.Repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private CourierRepository courierRepository;

    public ResponseEntity<?> insertNewDelivery(DeliveryDTO deliveryDTO) {
        Delivery newDelivery = new Delivery();
        newDelivery.setPickingAddress(deliveryDTO.getPickingAddress());
        Set<Package> packageList = new HashSet<>(deliveryDTO.getPackages().size());
        for(PackageDTO newPackageDTO: deliveryDTO.getPackages()) {
            Package pk = new Package();
            pk.setDeliveryAddress(newPackageDTO.getDeliveryAddress());
            pk.setStatus(PackageStatus.NEW);
            pk.setPayOnDelivery(newPackageDTO.isPayOnDelivery());
            pk.setCreatedOn(LocalDateTime.now());
            packageList.add(pk);
        }
        newDelivery.setPackageSet(packageList);
        newDelivery.setCreateDate(LocalDateTime.now());
        newDelivery.setPrice(deliveryDTO.getPrice());
        newDelivery.setCourier(null);
        packageRepository.saveAll(packageList);
        deliveryRepository.save(newDelivery);
        return ResponseEntity.ok(newDelivery);
    }

    public ResponseEntity<?> findByID(String awb) {
        Optional<Delivery> order = deliveryRepository.findById(awb);
        if(order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nu exista");
        }
    }

    public ResponseEntity<?> deleteDelivery(String awb) {
        Optional<Delivery> order = deliveryRepository.findById(awb);
        if(order.isPresent()) {
            deliveryRepository.delete(order.get());
            return ResponseEntity.ok("Comanda a fost cancelata");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nu exista");
        }
    }

    public ResponseEntity<?> getAllDeliveries() {
        return ResponseEntity.ok(deliveryRepository.findAllByOrderByCourierDesc());
    }

    public ResponseEntity<?> getMyDeliveries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Courier> courier = courierRepository.findByEmail(authentication.getName());
        return ResponseEntity.ok(deliveryRepository.findByCourier(courier.get()));
    }

    public ResponseEntity<?> setCourier(String awb, Integer id) {
        Optional<Courier> courier = courierRepository.findById(id);
        Optional<Delivery> delivery = deliveryRepository.findById(awb);
        delivery.get().setCourier(courier.get());
        deliveryRepository.save(delivery.get());
        return ResponseEntity.ok(new APIMessage(HttpStatus.OK, "Courierul a fost setat!"));
    }
}
