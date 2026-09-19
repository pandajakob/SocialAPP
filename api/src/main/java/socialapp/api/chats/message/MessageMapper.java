package socialapp.api.chats.message;

import org.springframework.stereotype.Component;
import socialapp.api.chats.message.DTO.MessageResponseDTO;
import socialapp.api.users.UserMapper;

@Component
public class MessageMapper {
    UserMapper userMapper = new UserMapper();

    public MessageResponseDTO toDTO(Message message) {
        return new MessageResponseDTO(
                message.getId(),
                userMapper.toDTO(message.getSender()),
                message.getContent(),
                message.getState(),
                message.getCreatedAt()
        );
    }
}
