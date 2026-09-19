package socialapp.backend.location.googleMaps.exceptions;

public class GoogleLocationNotFoundException extends RuntimeException {
    public GoogleLocationNotFoundException(String message) {
        super(message);
    }
}
