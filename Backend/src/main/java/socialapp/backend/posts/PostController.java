package socialapp.backend.posts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialapp.backend.posts.DTO.LocationDTO;
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
        List<PostResponseDTO> response = postService.getAllPosts();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/withinMeters")
    ResponseEntity<List<PostResponseDTO>> getAllPostsWithinMeters(@RequestBody PostsWithinMetersDTO postsWithinMetersDTO) {
        List<PostResponseDTO> response = postService.getAllPostsWithinMeters(postsWithinMetersDTO);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/nearest")
    ResponseEntity<List<PostResponseDTO>> getAllPostsWithinMeters(@RequestBody LocationDTO locationDTO) {
        List<PostResponseDTO> response = postService.getNearest(locationDTO);
        return ResponseEntity.ok().body(response);
    }




    @GetMapping("/{id}")
    ResponseEntity<PostResponseDTO> getPostById(@PathVariable UUID id) {
        PostResponseDTO response = postService.getPostById(id);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deletePosts(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.ok("successfully deleted post with id: " + id);
    }

    @ExceptionHandler(value = PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlePostNotFoundException(PostNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

}
