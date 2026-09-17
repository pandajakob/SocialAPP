package socialapp.backend.authentication;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialapp.backend.users.DTO.StandardUserResponseDTO;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    ResponseEntity<?> isAuthenticated() {
        return ResponseEntity.ok()
                .body(Map.of("message", "User is authenticated"));
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginDTO loginDetails) {
        ResponseCookie cookie = authService.login(loginDetails);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("message", "Login successful"));
    }

    @GetMapping("/logout")
    ResponseEntity<?> logout() {
        ResponseCookie cookie = authService.logout();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("message", "logout successful"));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    StandardUserResponseDTO register(@RequestBody RegisterDTO loginDetails) {
        return authService.register(loginDetails);
    }
}
