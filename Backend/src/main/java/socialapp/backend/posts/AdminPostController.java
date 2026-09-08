package socialapp.backend.posts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/posts")
public class AdminPostController {
    private PostServiceImpl postService;

    public AdminPostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostsById(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.ok("successfully deleted post with id: " + id);
    }
}
