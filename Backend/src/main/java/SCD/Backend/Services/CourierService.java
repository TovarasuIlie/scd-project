package SCD.Backend.Services;

import SCD.Backend.Models.Role;
import SCD.Backend.Repositories.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CourierService {
    @Autowired
    private CourierRepository courierRepository;


    public ResponseEntity<?> getCouriers() {
        return ResponseEntity.status(HttpStatus.OK).body(courierRepository.findAllByRole(Role.ROLE_COURIER));
    }
}
