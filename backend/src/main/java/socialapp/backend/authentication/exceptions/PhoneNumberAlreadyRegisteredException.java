package socialapp.backend.authentication.exceptions;

public class PhoneNumberAlreadyRegisteredException extends RuntimeException {
    private String message;

    public PhoneNumberAlreadyRegisteredException() {}

    public PhoneNumberAlreadyRegisteredException(String msg) {
        super(msg);
        this.message = msg;
    }
}

