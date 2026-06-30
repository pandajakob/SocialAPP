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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminUserControllerTest {

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    AdminUserController userController;

    @BeforeEach
    void setUp() {
        userController = new AdminUserController(userService);
    }

    @Test
    void findByEmailThrowsExceptionWhenUserDoesNotExist() {
        when(userService.getUserByEmail(any())).thenThrow(new NoSuchUserExistsException());

        assertThrows(NoSuchUserExistsException.class, () -> userController.getUserByEmail("email"));
    }

    @Test
    void findByEmailReturnsUserDTO() {
        String email = "email@test.com";

        StandardUserResponseDTO dto = new StandardUserResponseDTO(UUID.randomUUID(), "firstname", "lastname", email, 10, List.of(new Category("name", null)), "12345678");

        when(userService.getUserByEmail(any())).thenReturn(dto);

        assertEquals(200, userController.getUserByEmail(email).getStatusCode().value());
    }


    @Test
    void findByIdReturnsUserDTO() {
        UUID uuid = UUID.randomUUID();
        StandardUserResponseDTO dto = new StandardUserResponseDTO(uuid, "firstname", "lastname", "email@user.com", 10, List.of(new Category("name", null)), "12345678");

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



}