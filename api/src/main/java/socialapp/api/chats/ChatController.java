package socialapp.api.chats;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import socialapp.api.chats.DTO.ChatCreateDTO;
import socialapp.api.chats.DTO.ChatResponseDTO;
import socialapp.api.chats.message.DTO.MessageRequestDTO;

import java.util.List;


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
        System.out.println("TEST: " + chat.createdAt().toString());
        return ResponseEntity.ok().body(chat);
    }

}