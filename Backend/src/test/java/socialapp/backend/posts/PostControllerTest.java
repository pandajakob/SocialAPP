package socialapp.backend.posts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import socialapp.backend.location.LocationDTO;
import socialapp.backend.posts.DTO.PostCreateDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;
import socialapp.backend.posts.exceptions.PostNotFoundException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Mock
    PostService postService;

    @InjectMocks
    PostController postController;

    @Mock
    Authentication authentication;

    private PostResponseDTO testPostResponseDTO;
    private PostCreateDTO testPostCreateDTO;
    private LocationDTO testLocationDTO;

    @BeforeEach
    void setUp() {
        postController = new PostController(postService);

        testPostResponseDTO = new PostResponseDTO(
                UUID.randomUUID(),
                Instant.now(),
                "Jane",
                "Doe",
                null,
                "Test Post",
                "Test Description",
                new LocationDTO(12.5, 55.6, "Denmark", "Copenhagen", "Formatted Address"),
                List.of(),
                18,
                30,
                "http://example.com/photo.jpg"
        );

        testPostCreateDTO = new PostCreateDTO(
                "Test Post",
                "Test Description",
                new LocationDTO(12.5, 55.6, "Denmark", "Copenhagen", "Formatted Address"),
                List.of(),
                18,
                30,
                "http://example.com/photo.jpg"
        );

        testLocationDTO = new LocationDTO(12.5, 55.6, "Denmark", "Copenhagen", "Formatted Address");
    }


    @Test
    void getOwnPosts_returns200WithList() {
        when(postService.getOwnPosts(authentication)).thenReturn(List.of(testPostResponseDTO));

        ResponseEntity<List<PostResponseDTO>> response = postController.getOwnPosts(authentication);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getOwnPosts_returns200WithEmptyList() {
        when(postService.getOwnPosts(authentication)).thenReturn(List.of());

        ResponseEntity<List<PostResponseDTO>> response = postController.getOwnPosts(authentication);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getOwnPosts_delegatesToPostService() {
        when(postService.getOwnPosts(authentication)).thenReturn(List.of(testPostResponseDTO));

        postController.getOwnPosts(authentication);

        verify(postService).getOwnPosts(authentication);
    }

    @Test
    void getFeed_returns200WithList() {
        when(postService.getFeed(authentication, testLocationDTO)).thenReturn(List.of(testPostResponseDTO));

        ResponseEntity<List<PostResponseDTO>> response = 
                postController.getFeed(testLocationDTO, authentication);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getFeed_delegatesToPostService() {
        when(postService.getFeed(authentication, testLocationDTO)).thenReturn(List.of(testPostResponseDTO));

        postController.getFeed(testLocationDTO, authentication);

        verify(postService).getFeed(authentication, testLocationDTO);
    }

    @Test
    void createPost_returns200WithDTO() {
        when(postService.createPost(testPostCreateDTO, authentication)).thenReturn(testPostResponseDTO);

        ResponseEntity<PostResponseDTO> response = 
                postController.createPost(testPostCreateDTO, authentication);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(testPostResponseDTO, response.getBody());
    }

    @Test
    void createPost_delegatesToPostService() {
        when(postService.createPost(testPostCreateDTO, authentication)).thenReturn(testPostResponseDTO);

        postController.createPost(testPostCreateDTO, authentication);

        verify(postService).createPost(testPostCreateDTO, authentication);
    }

    @Test
    void getPostById_returns200WithDTO() {
        UUID postId = UUID.randomUUID();
        when(postService.getPostById(postId)).thenReturn(testPostResponseDTO);

        ResponseEntity<PostResponseDTO> response = postController.getPostById(postId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(testPostResponseDTO, response.getBody());
    }

    @Test
    void getPostById_delegatesToPostService() {
        UUID postId = UUID.randomUUID();
        when(postService.getPostById(postId)).thenReturn(testPostResponseDTO);

        postController.getPostById(postId);

        verify(postService).getPostById(postId);
    }

    @Test
    void getPostById_propagatesPostNotFoundException() {
        UUID postId = UUID.randomUUID();
        when(postService.getPostById(postId)).thenThrow(new PostNotFoundException(postId));

        assertThrows(PostNotFoundException.class,
                () -> postController.getPostById(postId));
    }
    
}
