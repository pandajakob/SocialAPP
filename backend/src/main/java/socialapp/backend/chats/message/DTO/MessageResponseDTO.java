package socialapp.backend.chats.message.DTO;

import socialapp.backend.chats.message.Message.MessageState;
import socialapp.backend.users.DTO.StandardUserResponseDTO;

import java.util.UUID;

public record MessageResponseDTO(
        UUID uuid,
        StandardUserResponseDTO sender,
        String content,
        MessageState state
) {}
