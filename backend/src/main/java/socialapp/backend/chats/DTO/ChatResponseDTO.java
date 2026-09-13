package socialapp.backend.chats.DTO;

import socialapp.backend.chats.message.DTO.MessageResponseDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;
import socialapp.backend.users.DTO.StandardUserResponseDTO;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record ChatResponseDTO(
        UUID id,
        PostResponseDTO post,
        List<MessageResponseDTO> messages,
        List<StandardUserResponseDTO> participants,
        Instant date
) { }
