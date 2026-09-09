package socialapp.backend.chats;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import socialapp.backend.Location.LocationDTO;
import socialapp.backend.chats.DTO.ChatCreateDTO;
import socialapp.backend.chats.DTO.ChatResponseDTO;
import socialapp.backend.chats.message.DTO.MessageRequestDTO;
import socialapp.backend.chats.message.DTO.MessageResponseDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;
import socialapp.backend.posts.PostService;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/chats")
public class ChatController {
    private ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    ResponseEntity<ChatResponseDTO> createChat(@RequestBody ChatCreateDTO chatCreateDTO, Authentication authentication) {
        ChatResponseDTO chatResponseDTO = chatService.createChat(chatCreateDTO, authentication);
        return ResponseEntity.ok().body(chatResponseDTO);
    }

    @GetMapping
    ResponseEntity<List<ChatResponseDTO>> getAllChatsForUser(Authentication authentication) {
        List<ChatResponseDTO> chats = chatService.getAllChatsForUser(authentication);
        return ResponseEntity.ok().body(chats);
    }

    @PostMapping("/message")
    ResponseEntity<ChatResponseDTO> sendMessage(@RequestBody MessageRequestDTO messageRequestDTO, Authentication authentication) {
        ChatResponseDTO chat = chatService.sendMessage(messageRequestDTO, authentication);
        return ResponseEntity.ok().body(chat);
    }

}