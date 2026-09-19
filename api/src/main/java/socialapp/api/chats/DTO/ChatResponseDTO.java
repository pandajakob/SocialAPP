package socialapp.api.chats.DTO;

import socialapp.api.chats.message.DTO.MessageResponseDTO;
import socialapp.api.posts.DTO.PostResponseDTO;
import socialapp.api.users.DTO.StandardUserResponseDTO;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ChatResponseDTO(
        UUID id,
        PostResponseDTO post,
        List<MessageResponseDTO> messages,
        List<StandardUserResponseDTO> participants,
        Instant createdAt
) { }
