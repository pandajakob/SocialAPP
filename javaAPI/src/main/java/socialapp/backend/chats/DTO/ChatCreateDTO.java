package socialapp.backend.chats.DTO;

import org.springframework.security.core.Authentication;


import java.util.UUID;

public record ChatCreateDTO(
        UUID postId,
        String messageContent)  { }
