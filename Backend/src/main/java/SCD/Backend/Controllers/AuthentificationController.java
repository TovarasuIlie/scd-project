package SCD.Backend.Controllers;

import SCD.Backend.DTOs.LoginDTO;
import SCD.Backend.DTOs.RegisterDTO;
import SCD.Backend.Models.APIMessage;
import SCD.Backend.Services.AuthentificationService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/Authentication")
@CrossOrigin
public class AuthentificationController {

    @Autowired
    private AuthentificationService authentificationService;

    @PostMapping("register-account")
    public ResponseEntity<?> registerAccount(@RequestBody RegisterDTO registerDTO) {
        return authentificationService.registerNewAccount(registerDTO);
    }

    @PostMapping("login-account")
    public ResponseEntity<?> loginInto(@RequestBody LoginDTO loginDTO) {
        return authentificationService.loginInAccount(loginDTO);
    }

    @PutMapping("verify-otp/{id}")
    public ResponseEntity<?> verifyOTP(@PathVariable Integer id, @RequestBody JsonNode OTP) {
        return authentificationService.verifyOTP(id, OTP);
    }

    @GetMapping("generate-code/{email}")
    public ResponseEntity<?> generateCode(@PathVariable String email) {
        return authentificationService.generateCode(email);
    }
}
