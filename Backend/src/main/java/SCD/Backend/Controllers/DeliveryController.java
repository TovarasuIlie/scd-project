package SCD.Backend.Controllers;

import SCD.Backend.DTOs.DeliveryDTO;
import SCD.Backend.DTOs.EditDeliveryDTO;
import SCD.Backend.Models.Delivery;
import SCD.Backend.Services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/Delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("insert-new-delivery")
    public ResponseEntity<?> insertNewDelivery(@RequestBody DeliveryDTO deliveryDTO) {
        return deliveryService.insertNewDelivery(deliveryDTO);
    }

    @PutMapping("edit-delivery")
    public ResponseEntity<?> editDelivery(@RequestBody EditDeliveryDTO editDeliveryDTO) {
        return deliveryService.updateDelivery(editDeliveryDTO);
    }

    @GetMapping("get-delivery-for/{id}")
    public ResponseEntity<?> getDeliveryFor(@PathVariable Integer id) {
        return deliveryService.getDeliveryForCourier(id);
    }

    @GetMapping("get-delivery/{awb}")
    public ResponseEntity<?> getDelivery(@PathVariable String awb) {
        return deliveryService.findByID(awb);
    }

    @GetMapping("get-all-deliveries")
    public ResponseEntity<?> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("get-my-deliveries")
    public ResponseEntity<?> getMyDeliveries() {
        return deliveryService.getMyDeliveries();
    }

    @PutMapping("set-courier-to-delivery/{awb}/{id}")
    public ResponseEntity<?> setCourierToDelivery(@PathVariable String awb, @PathVariable Integer id) {
        return deliveryService.setCourier(awb, id);
    }

    @PutMapping("set-delivery-status")
    public ResponseEntity<?> setDeliveryStatus(@RequestBody Delivery delivery) {
        return deliveryService.setStatus(delivery);
    }

    @PutMapping("remove-courier/{awb}")
    public ResponseEntity<?>  removeDelivery(@PathVariable String awb) {
        return deliveryService.removeCourier(awb);
    }

    @DeleteMapping("delete-delivery/{awb}")
    public ResponseEntity<?> deleteDelivery(@PathVariable String awb) {
        return deliveryService.deleteDelivery(awb);
    }

    @GetMapping("get-customer-deliveries/{email}")
    public ResponseEntity<?> getCustomerDeliveries(@PathVariable String email) {
        return deliveryService.findCustomerDeliveries(email);
    }

    @GetMapping("get-customer-pending-deliveries/{email}")
    public ResponseEntity<?> getCustomerPendingDeliveries(@PathVariable String email) {
        return deliveryService.findCustomerPendingDeliveries(email);
    }
}
