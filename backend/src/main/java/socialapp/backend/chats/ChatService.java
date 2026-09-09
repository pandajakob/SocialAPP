package socialapp.backend.chats;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import socialapp.backend.authentication.AuthService;
import socialapp.backend.chats.DTO.ChatCreateDTO;
import socialapp.backend.chats.DTO.ChatResponseDTO;
import socialapp.backend.chats.exceptions.ChatNotFoundException;
import socialapp.backend.chats.message.DTO.MessageRequestDTO;
import socialapp.backend.chats.message.Message;
import socialapp.backend.chats.message.MessageMapper;
import socialapp.backend.posts.Post;
import socialapp.backend.posts.PostMapper;
import socialapp.backend.posts.PostRepository;
import socialapp.backend.posts.exceptions.PostNotFoundException;
import socialapp.backend.users.User;
import socialapp.backend.users.UserMapper;

import java.util.List;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final ChatMapper chatMapper;

    public ChatService(ChatRepository chatRepository, AuthService authService, PostRepository postRepository, MessageMapper messageMapper, PostMapper postMapper, UserMapper userMapper, ChatMapper chatMapper) {
        this.chatRepository = chatRepository;
        this.authService = authService;
        this.postRepository = postRepository;
        this.chatMapper = chatMapper;
    }

    public ChatResponseDTO createChat(ChatCreateDTO chatCreateDTO, Authentication authentication) {
        User sender = authService.getUserFromAuth(authentication);
        Post post = postRepository
                .findById(chatCreateDTO.postId())
                .orElseThrow(() -> new PostNotFoundException(chatCreateDTO.postId()));

        User recipient = post.getCreatedBy();

        Chat chat = new Chat(post, sender, recipient);

        Message message = new Message(
                chatCreateDTO.messageContent(),
                sender,
                chat
        );

        chat.addMessage(message);

        Chat savedChat = chatRepository.save(chat);

        return chatMapper.toDTO(savedChat);
    }

    public List<ChatResponseDTO> getAllChatsForUser(Authentication authentication) {
        User sender = authService.getUserFromAuth(authentication);
        List<Chat> chats = chatRepository.findAllByUserId(sender.getId());

        return chats.stream().map(chatMapper::toDTO).toList();
    }

    public ChatResponseDTO sendMessage(MessageRequestDTO messageRequestDTO, Authentication authentication) {
        User sender = authService.getUserFromAuth(authentication);
        Chat chat = chatRepository
                .findById(messageRequestDTO.chatId())
                .orElseThrow(()->new ChatNotFoundException((messageRequestDTO.chatId())));
        Message message = new Message(messageRequestDTO.messageContent(), sender, chat);
        chat.addMessage(message);
        Chat savedChat = chatRepository.save(chat);
        return chatMapper.toDTO(savedChat);
    }
}
