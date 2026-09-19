package socialapp.backend.chats.exceptions;

import java.util.UUID;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException(UUID id) {
        super("chat with id: " + id.toString() + " could not found");
    }
}
