package SCD.Backend.Services;

import SCD.Backend.DTOs.LoggedUserDTO;
import SCD.Backend.DTOs.LoginDTO;
import SCD.Backend.DTOs.RegisterDTO;
import SCD.Backend.Models.APIMessage;
import SCD.Backend.Models.Courier;
import SCD.Backend.Repositories.CourierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthentificationService {

    private final CourierRepository courierRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthentificationService(AuthenticationManager authenticationManager, CourierRepository courierRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.courierRepository = courierRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public ResponseEntity<APIMessage> registerNewAccount(RegisterDTO registerDTO) {
        if(courierRepository.findByEmail(registerDTO.getEmail()).isEmpty()) {
            Courier newCourier = new Courier();
            newCourier.setEmail(registerDTO.getEmail());
            newCourier.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            newCourier.setName(registerDTO.getName());
            courierRepository.save(newCourier);
            return ResponseEntity.ok(new APIMessage(HttpStatus.CREATED, "Cont inserat!"));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIMessage(HttpStatus.CONFLICT, "Un cont este deja inregistrat pe acest email!"));
        }
    }

    public ResponseEntity<?> loginInAccount(LoginDTO loginDTO) {
        Optional<Courier> courier = courierRepository.findByEmail(loginDTO.getUsername());
        if(!courier.isEmpty()) {
            try {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
                if(authentication.isAuthenticated()) {
                    String jwt = jwtService.generateToken(courier.get());
                    return ResponseEntity.ok(new LoggedUserDTO(courier.get().getId(), courier.get().getEmail(), courier.get().getName(), jwt, jwt, jwt));
                }
            } catch (AuthenticationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "Numele de utilizator sau parola sunt gresite!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "Nu a fost gasit nici un email asociat contului!"));
        }
        return null;
    }
}
