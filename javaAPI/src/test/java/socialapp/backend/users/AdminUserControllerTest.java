package socialapp.backend.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import socialapp.backend.categories.Category;
import socialapp.backend.users.DTO.StandardUserResponseDTO;
import socialapp.backend.authentication.exceptions.NoSuchUserExistsException;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminUserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    AdminUserController userController;

    @BeforeEach
    void setUp() {
        userController = new AdminUserController(userService);
    }

    @Test
    void findByEmailThrowsExceptionWhenUserDoesNotExist() {
        when(userService.getUserByEmail(any())).thenThrow(new NoSuchUserExistsException());

        assertThrows(NoSuchUserExistsException.class, () -> userController.getUserByEmail("ghost@test.com"));
    }

    @Test
    void findByEmailReturnsUserDTO() {
        String email = "email@test.com";

        StandardUserResponseDTO dto = new StandardUserResponseDTO(UUID.randomUUID(), "firstname", "lastname", email, 10, List.of(new Category("key", "name", null, null)), "12345678");

        when(userService.getUserByEmail(any())).thenReturn(dto);

        assertEquals(200, userController.getUserByEmail(email).getStatusCode().value());
    }


    @Test
    void findByIdReturnsUserDTO() {
        UUID uuid = UUID.randomUUID();
        StandardUserResponseDTO dto = new StandardUserResponseDTO(uuid, "firstname", "lastname", "email@user.com", 10, List.of(new Category("key", "name", null, null)), "12345678");

        when(userService.getUserById(uuid)).thenReturn(dto);

        assertEquals(200, userController.getUserById(uuid).getStatusCode().value());
        assertEquals(dto, userController.getUserById(uuid).getBody());

    }

    @Test
    void findByIdThrowsExceptionWhenUserDoesNotExist() {
        when(userService.getUserById(any(UUID.class))).thenThrow(new NoSuchUserExistsException());
        assertThrows(NoSuchUserExistsException.class, () -> userController.getUserById(UUID.randomUUID()));
    }

    @Test
    void deleteUserThrowsExceptionWhenUserDoesNotExist() {
        UUID uuid = UUID.randomUUID();

        doThrow(new NoSuchUserExistsException())
                .when(userService)
                .deleteUser(uuid);

        assertThrows(NoSuchUserExistsException.class, () -> userController.deleteUser(uuid));
    }


    @Test
    void getAllUsers_returns200WithList() {
        StandardUserResponseDTO dto = new StandardUserResponseDTO(
                UUID.randomUUID(), "Jane", "Doe", "jane@example.com",
                28, List.of(), "+4512345678");
        when(userService.getAllUsers()).thenReturn(List.of(dto));

        var response = userController.getAllUsers();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getAllUsers_returns200WithEmptyList_whenNoUsersExist() {
        when(userService.getAllUsers()).thenReturn(List.of());

        var response = userController.getAllUsers();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void updateUser_returns200WithUpdatedDTO() {
        UUID uuid = UUID.randomUUID();
        User user = new User();
        StandardUserResponseDTO dto = new StandardUserResponseDTO(
                uuid, "Updated", "Doe", "jane@example.com",
                28, List.of(), "+4512345678");
        when(userService.updateUser(eq(uuid), any(User.class))).thenReturn(dto);

        var response = userController.updateUser(uuid, user);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
    }

    @Test
    void updateUser_throwsRuntimeException_whenUserNotFound() {
        UUID uuid = UUID.randomUUID();
        User user = new User();
        when(userService.updateUser(eq(uuid), any(User.class)))
                .thenThrow(new RuntimeException("User not found with id: " + uuid));

        assertThrows(RuntimeException.class,
                () -> userController.updateUser(uuid, user));
    }

    @Test
    void deleteUser_returns204_whenSuccessful() {
        UUID uuid = UUID.randomUUID();
        doNothing().when(userService).deleteUser(uuid);

        var response = userController.deleteUser(uuid);

        assertEquals(204, response.getStatusCode().value());
        verify(userService).deleteUser(uuid);
    }

    @Test
    void findByEmail_returnsBodyMatchingServiceResult() {
        String email = "email@test.com";
        UUID id = UUID.randomUUID();
        StandardUserResponseDTO dto = new StandardUserResponseDTO(
                id, "Jane", "Doe", email, 28, List.of(), "+4512345678");
        when(userService.getUserByEmail(any())).thenReturn(dto);

        var response = userController.getUserByEmail(email);

        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().id());
    }
}