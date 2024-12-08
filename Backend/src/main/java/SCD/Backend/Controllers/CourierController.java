package SCD.Backend.Controllers;

import SCD.Backend.Services.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/Courier")
@CrossOrigin
public class CourierController {

    @Autowired
    private CourierService courierService;

    @GetMapping("get-couriers")
    public ResponseEntity<?> getCouriers() {
        return courierService.getCouriers();
    }
}
