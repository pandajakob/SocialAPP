package socialapp.api.chats;

import org.springframework.stereotype.Component;
import socialapp.api.chats.DTO.ChatResponseDTO;
import socialapp.api.chats.message.MessageMapper;
import socialapp.api.posts.PostMapper;
import socialapp.api.users.UserMapper;

@Component
public class ChatMapper {
    private final PostMapper postMapper;
    private final MessageMapper messageMapper;
    private final UserMapper userMapper;

    public ChatMapper(PostMapper postMapper, MessageMapper messageMapper, UserMapper userMapper) {
        this.postMapper = postMapper;
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
    }

    public ChatResponseDTO toDTO(Chat chat) {
        return new ChatResponseDTO(
                chat.getId(),
                postMapper.toDTO(chat.getPost()),
                chat.getMessages().stream().map(messageMapper::toDTO).toList(),
                chat.getParticipants().stream().map(userMapper::toDTO).toList(),
                chat.getCreatedAt()
        );
    }
}
