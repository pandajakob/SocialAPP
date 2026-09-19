package socialapp.api.shared.exceptions;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import socialapp.api.authentication.exceptions.EmailAlreadyRegisteredException;
import socialapp.api.authentication.exceptions.NoSuchUserExistsException;
import socialapp.api.authentication.exceptions.PhoneNumberAlreadyRegisteredException;
import socialapp.api.authentication.exceptions.UserAlreadyRegisteredException;
import socialapp.api.categories.exceptions.NoSuchCategoryExistsException;
import socialapp.api.location.googleMaps.exceptions.GoogleApiCallException;
import socialapp.api.location.googleMaps.exceptions.GoogleLocationNotFoundException;
import socialapp.api.posts.exceptions.PostNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- 404 Not Found ---

    @ExceptionHandler(NoSuchUserExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchUserExistsException(NoSuchUserExistsException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(GoogleLocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse googleLocationNotFoundException(GoogleLocationNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlePostNotFoundException(PostNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(NoSuchCategoryExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchCategoryExistsException(NoSuchCategoryExistsException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    // --- 409 Conflict ---

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(PhoneNumberAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlePhoneNumberAlreadyRegisteredException(PhoneNumberAlreadyRegisteredException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    // --- 401 Unauthorized ---

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException ex) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid email or password");
    }

    @ExceptionHandler({JwtException.class, UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleJwtException(RuntimeException ex) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid or expired token");
    }

    // --- 500 Internal Server Error (catch-all) ---

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpectedException(Exception ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
    }

    // --- 503 Service Unavailable Error ---
    @ExceptionHandler(GoogleApiCallException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse googleApiCallException(GoogleApiCallException ex) {
        return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), "An unexpected error occurred");
    }

}
