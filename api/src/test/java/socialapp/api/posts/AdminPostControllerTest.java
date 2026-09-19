package socialapp.api.posts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import socialapp.api.posts.exceptions.PostNotFoundException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminPostControllerTest {

    @Mock
    PostService postService;

    @InjectMocks
    AdminPostController adminPostController;

    @BeforeEach
    void setUp() {
        adminPostController = new AdminPostController(postService);
    }

    @Test
    void deletePostsById_returns200WithSuccessMessage() {
        UUID postId = UUID.randomUUID();
        doNothing().when(postService).deletePost(postId);

        ResponseEntity<String> response = adminPostController.deletePostsById(postId);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains(postId.toString()));
    }

    @Test
    void deletePostsById_delegatesToPostService() {
        UUID postId = UUID.randomUUID();
        doNothing().when(postService).deletePost(postId);

        adminPostController.deletePostsById(postId);

        verify(postService).deletePost(postId);
    }

    @Test
    void deletePostsById_propagatesPostNotFoundException() {
        UUID postId = UUID.randomUUID();
        doThrow(new PostNotFoundException(postId)).when(postService).deletePost(postId);

        assertThrows(PostNotFoundException.class,
                () -> adminPostController.deletePostsById(postId));
    }

    @Test
    void deletePostsById_responseBodyContainsPostId() {
        UUID postId = UUID.randomUUID();
        doNothing().when(postService).deletePost(postId);

        ResponseEntity<String> response = adminPostController.deletePostsById(postId);

        assertTrue(response.getBody().contains("successfully deleted post with id: " + postId));
    }
}
