package socialapp.api.chats.message.DTO;

import socialapp.api.chats.message.Message.MessageState;
import socialapp.api.users.DTO.StandardUserResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record MessageResponseDTO(
        UUID uuid,
        StandardUserResponseDTO sender,
        String content,
        MessageState state,
        Instant createdAt
) {}
