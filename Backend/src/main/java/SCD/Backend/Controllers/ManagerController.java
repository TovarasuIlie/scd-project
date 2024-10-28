package SCD.Backend.Controllers;

import SCD.Backend.Services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/Manager")
@CrossOrigin
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @GetMapping("get-all-couriers")
    public ResponseEntity<?> getAllCouriers() {
        return managerService.getAllCouriers();
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getAll() {
        return managerService.getAll();
    }

    @GetMapping("get-courier/{id}")
    public ResponseEntity<?> getCourier(@PathVariable Integer id) {
        return managerService.getCourier(id);
    }

    @PutMapping("make-manager/{id}")
    public ResponseEntity<?> makeManager(@PathVariable Integer id) {
        return managerService.setRoleManager(id);
    }

    @PutMapping("make-courier/{id}")
    public ResponseEntity<?> makeCourier(@PathVariable Integer id) {
        return managerService.setRoleCourier(id);
    }

    @PutMapping("set-you-manager/{id}")
    public ResponseEntity<?> setYouManager(@PathVariable Integer id) {
        return managerService.setManager(id);
    }

    @PutMapping("remove-you-manager/{id}")
    public ResponseEntity<?> removeYouManager(@PathVariable Integer id) {
        return managerService.removeManager(id);
    }
}
