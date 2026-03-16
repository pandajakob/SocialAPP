package socialapp.backend.users;

import org.springframework.stereotype.Service;
import socialapp.backend.shared.domain_primitives.PhoneNumber;
import socialapp.backend.shared.domain_primitives.PhotoURL;
import socialapp.backend.users.DTO.CreateUserRequestDTO;
import socialapp.backend.users.DTO.StandardUserResponseDTO;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.Password;
import socialapp.backend.users.exceptions.EmailAlreadyRegisteredException;
import socialapp.backend.users.exceptions.NoSuchUserExistsException;
import socialapp.backend.users.exceptions.PhoneNumberAlreadyRegisteredException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public StandardUserResponseDTO createUser(CreateUserRequestDTO userDetails) {
        Email email = new Email(userDetails.email());
        PhotoURL profilePhotoUrl = new PhotoURL(userDetails.profilePhotoUrl());

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyRegisteredException("Email already registered: " + userDetails.email());
        }

        if (userRepository.existsByPhoneNumber(new PhoneNumber(userDetails.phoneNumber()))) {
            throw new PhoneNumberAlreadyRegisteredException("Phone number already registered: " + userDetails.phoneNumber());
        }

        User user = new User();
        user.setAge(userDetails.age());
        user.setEmail(email);
        user.setPhoneNumber(new PhoneNumber(userDetails.phoneNumber()));
        user.setFirstName(userDetails.firstName());
        user.setLastName(userDetails.lastName());
        user.setProfilePhotoUrl(profilePhotoUrl);
        user.setPassword(new Password(userDetails.password()));
        user.setRole(User.Role.USER);

        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
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
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
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
