package socialapp.backend.users;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import socialapp.backend.security.JWTService;
import socialapp.backend.users.DTO.StandardUserResponseDTO;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.authentication.exceptions.NoSuchUserExistsException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JWTService jwtService;

    public UserServiceImpl(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public List<StandardUserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StandardUserResponseDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserExistsException("User not found with id: " + id));
        return convertToDTO(user);
    }

    public StandardUserResponseDTO getUserByEmail(Email email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchUserExistsException("User not found with email: " + email));
        return convertToDTO(user);
    }

    public StandardUserResponseDTO updateUser(UUID id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (updatedUser.getFirstName() != null) {
            user.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            user.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getAge() != null) {
            user.setAge(updatedUser.getAge());
        }
        if (updatedUser.getInterests() != null) {
            user.setInterests(updatedUser.getInterests());
        }
        if (updatedUser.getPhoneNumber() != null) {
            if (!user.getPhoneNumber().equals(updatedUser.getPhoneNumber()) &&
                userRepository.existsByPhoneNumber(updatedUser.getPhoneNumber())) {
                throw new RuntimeException("Phone number already registered: " + updatedUser.getPhoneNumber());
            }
            user.setPhoneNumber(updatedUser.getPhoneNumber());
        }
        if (updatedUser.getProfilePhotoUrl() != null) {
            user.setProfilePhotoUrl(updatedUser.getProfilePhotoUrl());
        }
        if (updatedUser.getEmail() != null) {
            if (!user.getEmail().equals(updatedUser.getEmail()) &&
                userRepository.existsByEmail(updatedUser.getEmail())) {
                throw new RuntimeException("Email already registered: " + updatedUser.getEmail());
            }
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getRole() != null) {
            user.setRole(updatedUser.getRole());
        }

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchUserExistsException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public StandardUserResponseDTO getUserFromToken(Authentication authentication) {
        Email email = new Email(authentication.getName());
        return this.getUserByEmail(email);
    }

    private StandardUserResponseDTO convertToDTO(User user) {
        return new StandardUserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail().getValue(),
                user.getAge(),
                user.getInterests(),
                user.getPhoneNumber().getValue()
        );
    }
}
