package socialapp.backend.posts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialapp.backend.posts.DTO.PostCreateDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    ResponseEntity<PostResponseDTO> createPost(@RequestBody PostCreateDTO postCreateDTO) {
        try {
            PostResponseDTO response = postService.createPost(postCreateDTO);
            return ResponseEntity.ok().body(response);
        }  catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        try {
            List<PostResponseDTO> response = postService.getAllPosts();
            return ResponseEntity.ok().body(response);
        }  catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
