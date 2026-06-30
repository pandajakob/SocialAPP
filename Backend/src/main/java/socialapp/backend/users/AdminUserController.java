package socialapp.backend.users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.users.DTO.StandardUserResponseDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserServiceImpl userService;

    public AdminUserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<StandardUserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardUserResponseDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<StandardUserResponseDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(new Email(email)));
    }


    @PutMapping("/{id}")
    public ResponseEntity<StandardUserResponseDTO> updateUser(@PathVariable UUID id, @RequestBody User user) {
        StandardUserResponseDTO updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



}
