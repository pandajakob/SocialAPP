package socialapp.backend.chats;

import org.springframework.stereotype.Component;
import socialapp.backend.chats.DTO.ChatResponseDTO;
import socialapp.backend.chats.message.MessageMapper;
import socialapp.backend.posts.PostMapper;
import socialapp.backend.users.UserMapper;

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
                chat.getDate()
        );
    }
}
