package socialapp.backend.posts.exceptions;

import java.util.UUID;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(UUID id) {
        super("Post with id: " + id.toString() + " could not found");
    }
}
