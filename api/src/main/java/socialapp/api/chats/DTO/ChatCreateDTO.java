package socialapp.api.chats.DTO;


import java.util.UUID;

public record ChatCreateDTO(
        UUID postId,
        String messageContent)  { }
