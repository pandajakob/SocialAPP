package socialapp.api.categories.exceptions;


public class NoSuchCategoryExistsException extends RuntimeException {
    private String message;

    public NoSuchCategoryExistsException() {}

    public NoSuchCategoryExistsException(String msg) {
        super(msg);
        this.message = msg;
    }
}
