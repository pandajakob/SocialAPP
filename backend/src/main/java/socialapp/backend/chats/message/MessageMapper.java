package socialapp.backend.chats.message;

import org.springframework.stereotype.Component;
import socialapp.backend.chats.message.DTO.MessageResponseDTO;
import socialapp.backend.users.UserMapper;

@Component
public class MessageMapper {
    UserMapper userMapper = new UserMapper();

    public MessageResponseDTO toDTO(Message message) {
        return new MessageResponseDTO(
                message.getId(),
                userMapper.toDTO(message.getSender()),
                message.getContent(),
                message.getState()
        );
    }
}
