package SCD.Backend.Services;

import SCD.Backend.DTOs.EditDeliveryDTO;
import SCD.Backend.DTOs.EditPackageDTO;
import SCD.Backend.DTOs.PackageDTO;
import SCD.Backend.DTOs.DeliveryDTO;
import SCD.Backend.Models.*;
import SCD.Backend.Models.Package;
import SCD.Backend.Repositories.CourierRepository;
import SCD.Backend.Repositories.DeliveryRepository;
import SCD.Backend.Repositories.PackageRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private EmailService emailService;

    @Transactional
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
            pk.setPhoneNumber(newPackageDTO.getPhoneNumber());
            packageList.add(pk);
        }
        newDelivery.setPackageSet(packageList);
        newDelivery.setCreateDate(LocalDateTime.now());
        newDelivery.setPrice(deliveryDTO.getPrice());
        newDelivery.setPhoneNumber(deliveryDTO.getPhoneNumber());
        newDelivery.setEmail(deliveryDTO.getEmail());
        String specialKey = UUID.randomUUID().toString();
        newDelivery.setSpecialKey(specialKey);
        newDelivery.setCourier(null);
        newDelivery.setStatus(PackageStatus.NEW);
        packageRepository.saveAll(packageList);
        Delivery delivery = deliveryRepository.saveAndFlush(newDelivery);
        try {
            emailService.sendEmail(newDelivery, "Comanda cu AWB-ul: " + newDelivery.getAWB() + " a fost adaugata cu succes. Pentru a putea edita comanda, aveti urmatorul cod special: " + specialKey + ".", "Livrare adaugare cu succes!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "A avut loc o eroare la trimiterea email-ului."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(delivery);
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
            return ResponseEntity.ok(new APIMessage(HttpStatus.OK, "Comanda a fost stearsa"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Comanda nu exista"));
        }
    }

    public ResponseEntity<?> getAllDeliveries() {
        return ResponseEntity.ok(deliveryRepository.findAllByOrderByCourierDesc());
    }

    public ResponseEntity<?> getMyDeliveries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Courier> courier = courierRepository.findByEmail(authentication.getName());
        return ResponseEntity.ok(deliveryRepository.findByCourierStatus(courier.get().getId()));
    }

    public ResponseEntity<?> setCourier(String awb, Integer id) {
        Optional<Courier> courier = courierRepository.findById(id);
        Optional<Delivery> delivery = deliveryRepository.findById(awb);
        delivery.get().setCourier(courier.get());
        deliveryRepository.save(delivery.get());
        return ResponseEntity.ok(new APIMessage(HttpStatus.OK, "Courierul a fost setat!"));
    }

    @Transactional
    public ResponseEntity<?> setStatus(Delivery delivery) {
        deliveryRepository.save(delivery);
        packageRepository.updatePackagesStatusFromDelivery(delivery.getAWB(), delivery.getStatus().name());
        try {
            emailService.sendEmail(delivery, "Statusul livrarii cu AWB: " + delivery.getAWB() + " a fost setata ca si " + delivery.getStatus().name(), "Status livrare " + delivery.getAWB());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(new APIMessage(HttpStatus.OK, "Statusul a fost actualizat!"));
    }

    public ResponseEntity<?> removeCourier(String awb) {
        Optional<Delivery> delivery = deliveryRepository.findById(awb);
        delivery.get().setCourier(null);
        deliveryRepository.save(delivery.get());
        return ResponseEntity.ok(new APIMessage(HttpStatus.OK, "Curierul a fost inlaturat cu succes!"));
    }

    public ResponseEntity<?> updateDelivery(EditDeliveryDTO editDeliveryDTO) {
        Optional<Delivery> delivery = deliveryRepository.findById(editDeliveryDTO.getAwb());
        if(delivery.isPresent()) {
            if(delivery.get().getStatus().equals(PackageStatus.NEW)) {
                if(delivery.get().getSpecialKey().equals(editDeliveryDTO.getSpecialKey())) {
                    for(Iterator<EditPackageDTO> it = editDeliveryDTO.getPackages().iterator(); it.hasNext();) {
                        EditPackageDTO editPackageDTO = it.next();
                        Package pk = packageRepository.findById(editPackageDTO.getId()).get();
                        pk.setDeliveryAddress(editPackageDTO.getDeliveryAddress());
                        pk.setPhoneNumber(editPackageDTO.getPhoneNumber());
                        packageRepository.save(pk);
                    }
                    delivery.get().setPhoneNumber(editDeliveryDTO.getPhoneNumber());
                    delivery.get().setPickingAddress(editDeliveryDTO.getPickingAddress());
                    delivery.get().setPrice(editDeliveryDTO.getPrice());
                    delivery = Optional.of(deliveryRepository.saveAndFlush(delivery.get()));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Cheia speciala asociata comenzi nu corespunde sau este gresita!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Nu poti sa schimbi datele unei livrari care a fost atribuita unui curier!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Livrare nu a fost gasita!"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(delivery);
    }

    public ResponseEntity<?> getDeliveryForCourier(Integer id) {
        Courier courier = courierRepository.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(deliveryRepository.findByCourier(courier));
    }

    public ResponseEntity<?> findCustomerDeliveries(String email) {
        return ResponseEntity.ok(deliveryRepository.findByEmail(email));
    }

    public ResponseEntity<?> findCustomerPendingDeliveries(String email) {
        return ResponseEntity.ok(deliveryRepository.findByEmailAndStatus(email, PackageStatus.PENDING));
    }
}
