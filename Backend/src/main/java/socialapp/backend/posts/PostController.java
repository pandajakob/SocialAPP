package socialapp.backend.posts;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import socialapp.backend.location.LocationDTO;
import socialapp.backend.posts.DTO.PostCreateDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    ResponseEntity<List<PostResponseDTO>> getOwnPosts(Authentication authentication) {
        List<PostResponseDTO> response = postService.getOwnPosts(authentication);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/feed")
    ResponseEntity<List<PostResponseDTO>> getFeed(Authentication authentication, LocationDTO locationDTO) {
        List<PostResponseDTO> response = postService.getFeed(authentication, locationDTO);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    ResponseEntity<PostResponseDTO> createPost(@RequestBody PostCreateDTO postCreateDTO, Authentication authentication) {
        PostResponseDTO response = postService.createPost(postCreateDTO, authentication);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<PostResponseDTO> getPostById(@PathVariable UUID id) {
        PostResponseDTO response = postService.getPostById(id);
        return ResponseEntity.ok().body(response);
    }
}
