package SCD.Backend.Services;

import SCD.Backend.Models.APIMessage;
import SCD.Backend.Models.Courier;
import SCD.Backend.Models.Role;
import SCD.Backend.Repositories.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ManagerService {
    @Autowired
    private CourierRepository courierRepository;

    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(courierRepository.findAllByOrderByIdAsc());
    }

    public ResponseEntity<?> getAllCouriers() {
        return ResponseEntity.ok(courierRepository.findAllByRole(Role.ROLE_COURIER));
    }


    public ResponseEntity<?> getCourier(Integer id) {
        return ResponseEntity.ok(courierRepository.findById(id));
    }

    public ResponseEntity<?> setRoleManager(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Courier> you = courierRepository.findByEmail(authentication.getName());
        Courier courier = courierRepository.findById(id).get();
        if(you.equals(courier)) {
            courier.setManager(null);
            courier.setRole(Role.ROLE_MANAGER);
            courierRepository.save(courier);
            return ResponseEntity.ok(new APIMessage(HttpStatus.OK, "Courierul a fost promovat ca si manager!"));
        } else {
            return ResponseEntity.ok(new APIMessage(HttpStatus.CONFLICT, "Nu iti poti seta singur rolul!"));
        }
    }

    @Transactional
    public ResponseEntity<?> setRoleCourier(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Courier> you = courierRepository.findByEmail(authentication.getName());
        Courier courier = courierRepository.findById(id).get();
        if(you.equals(courier)) {
            courier.setManager(null);
            courier.setRole(Role.ROLE_COURIER);
            courierRepository.save(courier);
            courierRepository.updateAllCouriersManager(id);
            return ResponseEntity.ok(new APIMessage(HttpStatus.OK, "Courierul a fost promovat ca si courier!"));
        } else {
            return ResponseEntity.ok(new APIMessage(HttpStatus.CONFLICT, "Nu iti poti seta singur rolul!"));
        }
    }

    public ResponseEntity<?> setManager(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Courier> you = courierRepository.findByEmail(authentication.getName());
        Courier courier = courierRepository.findById(id).get();
        if(!you.get().equals(courier)) {
            if(!courier.getRole().equals(Role.ROLE_MANAGER)) {
                if(courier.getManager() == null) {
                    courier.setManager(you.get());
                    courierRepository.save(courier);
                    return ResponseEntity.ok(new APIMessage(HttpStatus.OK, "Courierul este acuma in custodia ta!"));
                } else {
                    return ResponseEntity.ok(new APIMessage(HttpStatus.CONFLICT, "Curierul este manageriat deja de un alt manager!"));
                }
            } else {
                return ResponseEntity.ok(new APIMessage(HttpStatus.CONFLICT, "Nu poti sa te setezi Manager al unui manager"));
            }
        } else {
            return ResponseEntity.ok(new APIMessage(HttpStatus.CONFLICT, "Nu iti poti seta singur rolul!"));
        }
    }

    public ResponseEntity<?> removeManager(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Courier> you = courierRepository.findByEmail(authentication.getName());
        Courier courier = courierRepository.findById(id).get();
        if(!you.equals(courier)) {
            if(!courier.getRole().equals(Role.ROLE_MANAGER)) {
                if(courier.getManager().equals(you.get())) {
                    courier.setManager(null);
                    courierRepository.save(courier);
                    return ResponseEntity.ok(new APIMessage(HttpStatus.OK, "Courierul nu mai este acuma in custodia ta!"));
                } else {
                    return ResponseEntity.ok(new APIMessage(HttpStatus.CONFLICT, "Curierul este manageriat deja de un alt manager!"));
                }
            } else {
                return ResponseEntity.ok(new APIMessage(HttpStatus.CONFLICT, "Nu poti sa te setezi Manager al unui manager"));
            }
        } else {
            return ResponseEntity.ok(new APIMessage(HttpStatus.CONFLICT, "Nu iti poti seta singur rolul!"));
        }
    }
}
