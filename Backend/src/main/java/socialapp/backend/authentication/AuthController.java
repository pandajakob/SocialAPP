package socialapp.backend.authentication;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialapp.backend.authentication.exceptions.*;
import socialapp.backend.users.DTO.StandardUserResponseDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginDTO loginDetails) {
        ResponseCookie cookie = authService.login(loginDetails);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Login successful");

    }
    @PostMapping("/register")
    ResponseEntity<StandardUserResponseDTO> register(@RequestBody RegisterDTO loginDetails) {
        return ResponseEntity.ok().body(authService.register(loginDetails));
    }


    @ExceptionHandler(value = EmailAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(value = PhoneNumberAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlePhoneNumberAlreadyRegisteredException(PhoneNumberAlreadyRegisteredException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(value = UserAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(value = NoSuchUserExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchUserExistsException(NoSuchUserExistsException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

}
