package socialapp.backend.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import socialapp.backend.authentication.exceptions.EmailAlreadyRegisteredException;
import socialapp.backend.authentication.exceptions.NoSuchUserExistsException;
import socialapp.backend.authentication.exceptions.PhoneNumberAlreadyRegisteredException;
import socialapp.backend.users.AdminUserController;
import socialapp.backend.users.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    @Mock
    AuthService authService;

    @InjectMocks
    AuthController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthController(authService);
    }
    @Test
    void assertHandleNoSuchUserExceptionReturnsStatuscode404() {
        assertEquals(404, authController.handleNoSuchUserExistsException(new NoSuchUserExistsException()).getStatusCode());
    }

    @Test
    void assertHandleEmailAlreadyRegisteredExceptionStatuscode409() {
        assertEquals(409, authController.handleEmailAlreadyRegisteredException(new EmailAlreadyRegisteredException()).getStatusCode());
    }

    @Test
    void assertHandlePhoneNumberAlreadyRegisteredExceptionStatuscode409() {
        assertEquals(409, authController.handlePhoneNumberAlreadyRegisteredException(new PhoneNumberAlreadyRegisteredException()).getStatusCode());
    }

}