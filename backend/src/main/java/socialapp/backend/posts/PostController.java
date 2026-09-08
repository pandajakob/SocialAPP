package socialapp.backend.posts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import socialapp.backend.Location.LocationDTO;
import socialapp.backend.posts.DTO.PostCreateDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;
import socialapp.backend.posts.DTO.PostsWithinMetersDTO;
import socialapp.backend.posts.exceptions.PostNotFoundException;
import socialapp.backend.authentication.exceptions.ErrorResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostServiceImpl postService;

    public PostController(PostServiceImpl postService) {
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
    ResponseEntity<PostResponseDTO> createPost(@RequestBody PostCreateDTO postCreateDTO,  Authentication authentication) {
        try {
            PostResponseDTO response = postService.createPost(postCreateDTO, authentication);
            return ResponseEntity.ok().body(response);
        }  catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<PostResponseDTO> getPostById(@PathVariable UUID id) {
        PostResponseDTO response = postService.getPostById(id);
        return ResponseEntity.ok().body(response);
    }

    @ExceptionHandler(value = PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlePostNotFoundException(PostNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

}
