package socialapp.backend.users;

import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.Password;
import socialapp.backend.shared.domain_primitives.PhoneNumber;
import socialapp.backend.users.DTO.CreateUserRequestDTO;
import socialapp.backend.users.DTO.StandardUserResponseDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface UserService {
    List<StandardUserResponseDTO> getAllUsers();

    StandardUserResponseDTO getUserById(UUID id);

    StandardUserResponseDTO getUserByEmail(Email email);

    StandardUserResponseDTO createUser(CreateUserRequestDTO userDetails);

    StandardUserResponseDTO updateUser(UUID id, User updatedUser);

    void deleteUser(UUID id);
}
