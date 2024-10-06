package SCD.Backend.Controllers;

import SCD.Backend.DTOs.LoginDTO;
import SCD.Backend.DTOs.RegisterDTO;
import SCD.Backend.Models.APIMessage;
import SCD.Backend.Services.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/Authentification")
public class AuthentificationController {

    @Autowired
    private AuthentificationService authentificationService;

    @PostMapping("register-account")
    public ResponseEntity<APIMessage> registerAccount(@RequestBody RegisterDTO registerDTO) {
        return authentificationService.registerNewAccount(registerDTO);
    }

    @PostMapping("login-into")
    public ResponseEntity<?> loginInto(@RequestBody LoginDTO loginDTO) {
        return authentificationService.loginInAccount(loginDTO);
    }
}
