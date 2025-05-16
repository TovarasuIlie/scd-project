package SCD.Backend.Services;

import SCD.Backend.DTOs.LoggedUserDTO;
import SCD.Backend.DTOs.LoginDTO;
import SCD.Backend.DTOs.RegisterDTO;
import SCD.Backend.Models.APIMessage;
import SCD.Backend.Models.Courier;
import SCD.Backend.Models.QRCode;
import SCD.Backend.Models.Role;
import SCD.Backend.Repositories.CourierRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthentificationService {

    private final CourierRepository courierRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MFAService mfaService;
    private final EmailService emailService;

    public AuthentificationService(AuthenticationManager authenticationManager, CourierRepository courierRepository, PasswordEncoder passwordEncoder, JWTService jwtService, MFAService mfaService, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.courierRepository = courierRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.mfaService = mfaService;
        this.emailService = emailService;
    }

    public ResponseEntity<?> registerNewAccount(RegisterDTO registerDTO) {
        if(courierRepository.findByEmail(registerDTO.getEmail()).isEmpty()) {
            String code = mfaService.generateNewSecret();
            Courier newCourier = new Courier();
            newCourier.setEmail(registerDTO.getEmail());
            newCourier.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            newCourier.setName(registerDTO.getName());
            newCourier.setRole(Role.ROLE_COURIER);
            newCourier.setSecret(code);
            newCourier = courierRepository.save(newCourier);
            QRCode qrCode = QRCode.builder().id(newCourier.getId()).image(mfaService.generateQrCodeImageUri(newCourier)).build();
            return ResponseEntity.status(HttpStatus.OK).body(qrCode);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIMessage(HttpStatus.CONFLICT, "Un cont este deja inregistrat pe acest email!"));
        }
    }

    public ResponseEntity<?> loginInAccount(LoginDTO loginDTO) {
        Optional<Courier> courier = courierRepository.findByEmail(loginDTO.getEmail());
        if(courier.isPresent()) {
            if(courier.get().isMfaEnabled()) {
                if(mfaService.isOtpValid(courier.get().getSecret(), loginDTO.getOtp())) {
                    try {
                        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
                        if(authentication.isAuthenticated()) {
                            String jwt = jwtService.generateToken(courier.get());
                            return ResponseEntity.ok(new LoggedUserDTO(courier.get().getId(), courier.get().getEmail(), courier.get().getName(), jwt));
                        }
                    } catch (AuthenticationException e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "Numele de utilizator sau parola sunt gresite!"));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIMessage(HttpStatus.UNAUTHORIZED, "Codul nu este corect!"));
                }
            } else {
                if(courier.get().getCode().equals(loginDTO.getOtp())) {
                    try {
                        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
                        if(authentication.isAuthenticated()) {
                            String jwt = jwtService.generateToken(courier.get());
                            return ResponseEntity.ok(new LoggedUserDTO(courier.get().getId(), courier.get().getEmail(), courier.get().getName(), jwt));
                        }
                    } catch (AuthenticationException e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "Numele de utilizator sau parola sunt gresite!"));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIMessage(HttpStatus.UNAUTHORIZED, "Codul nu este corect!"));
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "Nu a fost gasit nici un email asociat contului!"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new APIMessage(HttpStatus.UNAUTHORIZED, "Nu poti intra pe cont, daca nu ai codul de securitate."));
    }

    @Transactional
    public ResponseEntity<?> verifyOTP(Integer id, JsonNode otp) {
        Optional<Courier> courier = courierRepository.findById(id);
        if(courier.isPresent()) {
            if(mfaService.isOtpValid(courier.get().getSecret(), otp.asText())) {
                courier.get().setMfaEnabled(true);
                return ResponseEntity.ok(new APIMessage(HttpStatus.OK, "MFA-ul a fost activat!"));
            } else {
                return ResponseEntity.ok(new APIMessage(HttpStatus.UNAUTHORIZED, "Codul nu este valid!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Contul nu a fost gasit!"));
        }
    }

    @Transactional
    public ResponseEntity<?> generateCode(String email) {
        Optional<Courier> courier = courierRepository.findByEmail(email);
        if(courier.isPresent()) {
            courier.get().setCode(getSaltString());
            try {
                emailService.sendCode(courier.get(), "Codul de securitate este: " + courier.get().getCode(), "Cod OTP");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIMessage(HttpStatus.BAD_REQUEST, "Utilizatorul nu a fost gasit!"));
            }
            return ResponseEntity.ok(new APIMessage(HttpStatus.OK, "Codul a fost trimis pe email."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIMessage(HttpStatus.NOT_FOUND, "Utilizatorul nu a fost gasit!"));
        }
    }

    public static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}
