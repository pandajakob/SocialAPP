package socialapp.backend.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import socialapp.backend.authentication.exceptions.EmailAlreadyRegisteredException;
import socialapp.backend.authentication.exceptions.NoSuchUserExistsException;
import socialapp.backend.authentication.exceptions.PhoneNumberAlreadyRegisteredException;
import socialapp.backend.shared.exceptions.GlobalExceptionHandler;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void assertHandleNoSuchUserExceptionReturnsStatuscode404() {
        assertEquals(404, handler.handleNoSuchUserExistsException(new NoSuchUserExistsException()).getStatusCode());
    }

    @Test
    void assertHandleEmailAlreadyRegisteredExceptionStatuscode409() {
        assertEquals(409, handler.handleEmailAlreadyRegisteredException(new EmailAlreadyRegisteredException()).getStatusCode());
    }

    @Test
    void assertHandlePhoneNumberAlreadyRegisteredExceptionStatuscode409() {
        assertEquals(409, handler.handlePhoneNumberAlreadyRegisteredException(new PhoneNumberAlreadyRegisteredException()).getStatusCode());
    }
}
