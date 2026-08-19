package socialapp.backend.authentication.exceptions;


public class NoSuchUserExistsException extends RuntimeException {
    private String message;

    public NoSuchUserExistsException() {}

    public NoSuchUserExistsException(String msg) {
        super(msg);
        this.message = msg;
    }
}
