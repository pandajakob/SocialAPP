package socialapp.backend.location.googleMaps.exceptions;

public class GoogleApiCallException extends RuntimeException {
    public GoogleApiCallException(String message) {
        super(message);
    }
}
