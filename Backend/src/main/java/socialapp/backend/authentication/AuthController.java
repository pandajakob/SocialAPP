package socialapp.backend.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialapp.backend.authentication.AuthDTO;
import socialapp.backend.authentication.AuthService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    void login(@RequestBody AuthDTO.loginDTO loginDetails) {
        authService.login(loginDetails);
    }
}
