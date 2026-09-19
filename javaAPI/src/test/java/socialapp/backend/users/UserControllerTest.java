package socialapp.backend.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import socialapp.backend.authentication.exceptions.NoSuchUserExistsException;
import socialapp.backend.users.DTO.StandardUserResponseDTO;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Mock
    Authentication authentication;

    private StandardUserResponseDTO testDTO;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService);
        testDTO = new StandardUserResponseDTO(
                UUID.randomUUID(),
                "Jane",
                "Doe",
                "jane@example.com",
                28,
                List.of(),
                "+4512345678"
        );
    }

    // ─── GET /users/me ────────────────────────────────────────────────────────

    @Test
    void getUserFromToken_returns200WithDTO_whenTokenIsValid() {
        when(userService.getUserFromToken(authentication)).thenReturn(testDTO);

        ResponseEntity<StandardUserResponseDTO> response =
                userController.getUserFromToken(authentication);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(testDTO, response.getBody());
    }

    @Test
    void getUserFromToken_delegatesToUserService() {
        when(userService.getUserFromToken(authentication)).thenReturn(testDTO);

        userController.getUserFromToken(authentication);

        verify(userService, times(1)).getUserFromToken(authentication);
    }

    @Test
    void getUserFromToken_throwsNoSuchUserExistsException_whenEmailNotRegistered() {
        when(userService.getUserFromToken(authentication))
                .thenThrow(new NoSuchUserExistsException("User not found"));

        assertThrows(NoSuchUserExistsException.class,
                () -> userController.getUserFromToken(authentication));
    }

    @Test
    void getUserFromToken_responseBodyMatchesServiceResult() {
        UUID id = UUID.randomUUID();
        StandardUserResponseDTO specific = new StandardUserResponseDTO(
                id, "Alice", "Wonder", "alice@example.com", 30, List.of(), "+4500000001");
        when(userService.getUserFromToken(authentication)).thenReturn(specific);

        ResponseEntity<StandardUserResponseDTO> response =
                userController.getUserFromToken(authentication);

        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().id());
        assertEquals("Alice", response.getBody().firstName());
        assertEquals("alice@example.com", response.getBody().email());
    }
}
