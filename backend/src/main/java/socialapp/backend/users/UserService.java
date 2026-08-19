package socialapp.backend.users;

import org.springframework.security.core.Authentication;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.users.DTO.StandardUserResponseDTO;
import java.util.List;
import java.util.UUID;

public interface UserService {
    List<StandardUserResponseDTO> getAllUsers();

    StandardUserResponseDTO getUserById(UUID id);

    StandardUserResponseDTO getUserByEmail(Email email);

    StandardUserResponseDTO updateUser(UUID id, User updatedUser);

    void deleteUser(UUID id);
    StandardUserResponseDTO getUserFromToken(Authentication authentication);

}
