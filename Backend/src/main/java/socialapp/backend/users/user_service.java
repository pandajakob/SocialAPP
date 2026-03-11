package socialapp.backend.users;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class user_service {

    private final user_repository userRepository;

    public user_service(user_repository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToDTO(user);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return convertToDTO(user);
    }

    @Transactional
    public UserDTO createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered: " + user.getEmail());
        }
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new RuntimeException("Phone number already registered: " + user.getPhoneNumber());
        }

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Transactional
    public UserDTO updateUser(UUID id, User updatedUser) {
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

    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAge(user.getAge());
        dto.setInterests(user.getInterests());
        dto.setPhoneNumber(user.getPhoneNumber());
        if (user.getProfilePhotoUrl() != null) {
            dto.setProfilePhoto(new UserDTO.ProfilePhotoDTO(user.getProfilePhotoUrl()));
        }
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
}
