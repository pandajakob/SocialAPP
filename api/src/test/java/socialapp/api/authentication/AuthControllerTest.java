package socialapp.api.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import socialapp.api.authentication.exceptions.EmailAlreadyRegisteredException;
import socialapp.api.authentication.exceptions.NoSuchUserExistsException;
import socialapp.api.authentication.exceptions.PhoneNumberAlreadyRegisteredException;
import socialapp.api.shared.exceptions.GlobalExceptionHandler;

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
