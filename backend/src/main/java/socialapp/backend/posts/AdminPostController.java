package socialapp.backend.posts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialapp.backend.authentication.exceptions.ErrorResponse;
import socialapp.backend.posts.exceptions.PostNotFoundException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/posts")
public class AdminPostController {
    private PostServiceImpl postService;

    public AdminPostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePostsById(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.ok("successfully deleted post with id: " + id);
    }

    @ExceptionHandler(value = PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlePostNotFoundException(PostNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

}
