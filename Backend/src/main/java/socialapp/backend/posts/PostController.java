package socialapp.backend.posts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialapp.backend.posts.DTO.PostCreateDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;
import socialapp.backend.posts.exceptions.PostNotFoundException;
import socialapp.backend.users.exceptions.EmailAlreadyRegisteredException;
import socialapp.backend.users.exceptions.ErrorResponse;
import socialapp.backend.users.exceptions.NoSuchUserExistsException;
import socialapp.backend.users.exceptions.PhoneNumberAlreadyRegisteredException;

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
        try {
            List<PostResponseDTO> response = postService.getAllPosts();
            return ResponseEntity.ok().body(response);
        }  catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    ResponseEntity deletePosts(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.ok("successfully deleted post with id: "+ id);
    }


    @ExceptionHandler(value = PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlePostNotFoundException(PostNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

}
