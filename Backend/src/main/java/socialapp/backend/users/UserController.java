package socialapp.backend.users;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import socialapp.backend.users.DTO.StandardUserResponseDTO;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<StandardUserResponseDTO> getUserFromToken(Authentication authentication) {
        StandardUserResponseDTO user = userService.getUserFromToken(authentication);
        return ResponseEntity.ok(user);
    }

}
