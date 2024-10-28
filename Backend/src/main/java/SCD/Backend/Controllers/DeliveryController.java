package SCD.Backend.Controllers;

import SCD.Backend.DTOs.DeliveryDTO;
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

    @DeleteMapping("delete-delivery/{awb}")
    public ResponseEntity<?> deleteDelivery(@PathVariable String awb) {
        return deliveryService.deleteDelivery(awb);
    }
}
