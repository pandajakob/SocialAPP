package socialapp.api.location.googleMaps.exceptions;

public class GoogleLocationNotFoundException extends RuntimeException {
    public GoogleLocationNotFoundException(String message) {
        super(message);
    }
}
