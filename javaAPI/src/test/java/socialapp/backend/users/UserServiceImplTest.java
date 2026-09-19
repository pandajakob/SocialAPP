package socialapp.backend.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import socialapp.backend.authentication.exceptions.NoSuchUserExistsException;
import socialapp.backend.categories.Category;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.EncodedPassword;
import socialapp.backend.shared.domain_primitives.PhoneNumber;
import socialapp.backend.users.DTO.StandardUserResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    private User testUser;
    private StandardUserResponseDTO testDTO;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testUser = new User(
                "Jane",
                "Doe",
                28,
                new EncodedPassword("hashed-secret"),
                new Email("jane@example.com"),
                new PhoneNumber("+4512345678")
        );
        testUser.setId(testId);
        testUser.setInterests(List.of());
        testUser.setSavedPosts(List.of());

        testDTO = new StandardUserResponseDTO(
                testId,
                "Jane",
                "Doe",
                "jane@example.com",
                28,
                List.of(),
                "+4512345678"
        );
    }

    @Test
    void getAllUsers_returnsEmptyList_whenNoUsersExist() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<StandardUserResponseDTO> result = userService.getAllUsers();

        assertTrue(result.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void getAllUsers_returnsMappedDTOs() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        when(userMapper.toDTO(testUser)).thenReturn(testDTO);

        List<StandardUserResponseDTO> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals(testDTO, result.get(0));
    }

    @Test
    void getAllUsers_mapsEachUserExactlyOnce() {
        User second = new User("John", "Smith", 35,
                new EncodedPassword("pw"),
                new Email("john@example.com"),
                new PhoneNumber("+4587654321"));
        second.setId(UUID.randomUUID());
        second.setInterests(List.of());
        second.setSavedPosts(List.of());

        when(userRepository.findAll()).thenReturn(List.of(testUser, second));
        when(userMapper.toDTO(any(User.class))).thenReturn(testDTO);

        List<StandardUserResponseDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userMapper, times(2)).toDTO(any(User.class));
    }

    @Test
    void getUserById_returnsDTO_whenUserExists() {
        when(userRepository.findById(testId)).thenReturn(Optional.of(testUser));
        when(userMapper.toDTO(testUser)).thenReturn(testDTO);

        StandardUserResponseDTO result = userService.getUserById(testId);

        assertEquals(testDTO, result);
    }

    @Test
    void getUserById_throwsNoSuchUserExistsException_whenUserNotFound() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NoSuchUserExistsException.class,
                () -> userService.getUserById(UUID.randomUUID()));
    }

    @Test
    void getUserById_exceptionMessage_containsId() {
        UUID missingId = UUID.randomUUID();
        when(userRepository.findById(missingId)).thenReturn(Optional.empty());

        NoSuchUserExistsException ex = assertThrows(NoSuchUserExistsException.class,
                () -> userService.getUserById(missingId));

        assertTrue(ex.getMessage().contains(missingId.toString()));
    }

    @Test
    void getUserByEmail_returnsDTO_whenUserExists() {
        Email email = new Email("jane@example.com");
        when(userRepository.findByEmail(email.getValue())).thenReturn(Optional.of(testUser));
        when(userMapper.toDTO(testUser)).thenReturn(testDTO);

        StandardUserResponseDTO result = userService.getUserByEmail(email);

        assertEquals(testDTO, result);
    }

    @Test
    void getUserByEmail_throwsNoSuchUserExistsException_whenUserNotFound() {
        Email email = new Email("ghost@example.com");
        when(userRepository.findByEmail(email.getValue())).thenReturn(Optional.empty());

        assertThrows(NoSuchUserExistsException.class,
                () -> userService.getUserByEmail(email));
    }

    // ─── deleteUser ───────────────────────────────────────────────────────────

    @Test
    void deleteUser_deletesUser_whenUserExists() {
        when(userRepository.existsById(testId)).thenReturn(true);

        userService.deleteUser(testId);

        verify(userRepository).deleteById(testId);
    }

    @Test
    void deleteUser_throwsNoSuchUserExistsException_whenUserNotFound() {
        UUID missingId = UUID.randomUUID();
        when(userRepository.existsById(missingId)).thenReturn(false);

        assertThrows(NoSuchUserExistsException.class,
                () -> userService.deleteUser(missingId));

        verify(userRepository, never()).deleteById(any());
    }

    // ─── updateUser ───────────────────────────────────────────────────────────

    @Test
    void updateUser_throwsRuntimeException_whenUserNotFound() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> userService.updateUser(UUID.randomUUID(), testUser));
    }

    @Test
    void updateUser_updatesFirstName_whenProvided() {
        User patch = buildPatchUser("Updated", null, null, null, null, null);
        when(userRepository.findById(testId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDTO(any(User.class))).thenReturn(testDTO);

        userService.updateUser(testId, patch);

        assertEquals("Updated", testUser.getFirstName());
        verify(userRepository).save(testUser);
    }

    @Test
    void updateUser_updatesLastName_whenProvided() {
        User patch = buildPatchUser(null, "Smith", null, null, null, null);
        when(userRepository.findById(testId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDTO(any(User.class))).thenReturn(testDTO);

        userService.updateUser(testId, patch);

        assertEquals("Smith", testUser.getLastName());
    }

    @Test
    void updateUser_updatesAge_whenProvided() {
        User patch = buildPatchUser(null, null, 40, null, null, null);
        when(userRepository.findById(testId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDTO(any(User.class))).thenReturn(testDTO);

        userService.updateUser(testId, patch);

        assertEquals(40, testUser.getAge());
    }

    @Test
    void updateUser_updatesInterests_whenProvided() {
        Category cat = new Category("tech", "Tech", null, null);
        User patch = buildPatchUser(null, null, null, List.of(cat), null, null);
        when(userRepository.findById(testId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDTO(any(User.class))).thenReturn(testDTO);

        userService.updateUser(testId, patch);

        assertEquals(List.of(cat), testUser.getInterests());
    }

    @Test
    void updateUser_throwsRuntimeException_whenEmailAlreadyRegistered() {
        Email newEmail = new Email("taken@example.com");
        User patch = buildPatchUser(null, null, null, null, newEmail, null);
        when(userRepository.findById(testId)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByEmail(newEmail.getValue())).thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> userService.updateUser(testId, patch));

        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUser_allowsSameEmail_whenNotTakenByAnotherAccount() {
        // DomainPrimitive has no equals override, so the service always performs an
        // existsByEmail check when an email is provided. If the result is false
        // (not registered by another account) no exception should be thrown.
        Email sameEmail = new Email("jane@example.com");
        User patch = buildPatchUser(null, null, null, null, sameEmail, null);
        when(userRepository.findById(testId)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByEmail(sameEmail.getValue())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDTO(any(User.class))).thenReturn(testDTO);

        assertDoesNotThrow(() -> userService.updateUser(testId, patch));
    }

    @Test
    void updateUser_throwsRuntimeException_whenPhoneAlreadyRegistered() {
        PhoneNumber newPhone = new PhoneNumber("+4599999999");
        User patch = buildPatchUser(null, null, null, null, null, newPhone);
        when(userRepository.findById(testId)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByPhoneNumber(newPhone.getValue())).thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> userService.updateUser(testId, patch));

        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUser_savesAndReturnsMappedDTO() {
        User patch = buildPatchUser("New", null, null, null, null, null);
        when(userRepository.findById(testId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(testUser)).thenReturn(testUser);
        when(userMapper.toDTO(testUser)).thenReturn(testDTO);

        StandardUserResponseDTO result = userService.updateUser(testId, patch);

        assertEquals(testDTO, result);
        verify(userRepository).save(testUser);
    }

    // ─── getUserFromToken ──────────────────────────────────────────────────────

    @Test
    void getUserFromToken_returnsDTO_usingEmailFromAuthentication() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("jane@example.com");
        when(userRepository.findByEmail("jane@example.com")).thenReturn(Optional.of(testUser));
        when(userMapper.toDTO(testUser)).thenReturn(testDTO);

        StandardUserResponseDTO result = userService.getUserFromToken(auth);

        assertEquals(testDTO, result);
    }

    @Test
    void getUserFromToken_throwsNoSuchUserExistsException_whenEmailNotFound() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("ghost@example.com");
        when(userRepository.findByEmail("ghost@example.com")).thenReturn(Optional.empty());

        assertThrows(NoSuchUserExistsException.class,
                () -> userService.getUserFromToken(auth));
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    /**
     * Builds a partial User to use as an update patch. Null fields are intentionally
     * skipped by UserService.updateUser, mirroring real usage.
     */
    private User buildPatchUser(String firstName, String lastName, Integer age,
                                 List<Category> interests, Email email, PhoneNumber phone) {
        // Use a real constructor only when all required fields are present;
        // otherwise create via setters on a bare subclass / reflection-free approach.
        // Since User has no no-arg public constructor we use the full one and
        // then null-out what we don't want via spying is not possible cleanly —
        // instead, just supply sensible defaults and override with setters.
        Email safeEmail   = (email != null)   ? email   : new Email("patch@example.com");
        PhoneNumber safePhone = (phone != null) ? phone : new PhoneNumber("+4500000001");
        EncodedPassword safePwd = new EncodedPassword("pw");
        int safeAge = (age != null) ? age : 20;

        User patch = new User(
                firstName != null ? firstName : "First",
                lastName  != null ? lastName  : "Last",
                safeAge,
                safePwd,
                safeEmail,
                safePhone
        );
        patch.setInterests(interests != null ? interests : null);

        // Null-out fields the service checks via != null
        if (firstName == null) patch.setFirstName(null);
        if (lastName  == null) patch.setLastName(null);
        if (age       == null) patch.setAge(null);
        return patch;
    }
}
