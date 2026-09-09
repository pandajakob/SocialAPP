package socialapp.backend.chats.message.DTO;

import java.util.UUID;

public record MessageRequestDTO (UUID chatId, String messageContent) {}
