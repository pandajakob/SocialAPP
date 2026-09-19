package socialapp.backend.posts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import socialapp.backend.authentication.AuthService;
import socialapp.backend.categories.Category;
import socialapp.backend.feed.FeedRankingService;
import socialapp.backend.location.Location;
import socialapp.backend.location.LocationDTO;
import socialapp.backend.location.LocationService;
import socialapp.backend.posts.DTO.PostCreateDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;
import socialapp.backend.posts.exceptions.PostNotFoundException;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.EncodedPassword;
import socialapp.backend.shared.domain_primitives.PhoneNumber;
import socialapp.backend.users.User;
import socialapp.backend.users.UserRepository;
import socialapp.backend.users.DTO.StandardUserResponseDTO;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    LocationService locationService;

    @Mock
    FeedRankingService feedRankingService;

    @Mock
    AuthService authService;

    @Mock
    PostMapper postMapper;

    @InjectMocks
    PostService postService;

    private User testUser;
    private Post testPost;
    private Location testLocation;
    private PostResponseDTO testPostResponseDTO;
    private Authentication testAuthentication;

    @BeforeEach
    void setUp() {
        testUser = new User(
                "Jane",
                "Doe",
                28,
                new EncodedPassword("hashed-pw"),
                new Email("jane@example.com"),
                new PhoneNumber("+4512345678")
        );
        testUser.setId(UUID.randomUUID());
        testUser.setInterests(List.of());
        testUser.setSavedPosts(List.of());

        testLocation = new Location();
        testLocation.setId(UUID.randomUUID());

        testPost = new Post();
        testPost.setId(UUID.randomUUID());
        testPost.setCreatedBy(testUser);
        testPost.setLocation(testLocation);
        testPost.setTitle("Test Post");
        testPost.setDescription("Test Description");
        testPost.setAgeFrom(18);
        testPost.setAgeTo(30);
        testPost.setPhotoUrl("http://example.com/photo.jpg");
        testPost.setCategories(List.of(new Category("tech", "Tech", null, null)));

        testPostResponseDTO = new PostResponseDTO(
                testPost.getId(),
                Instant.now(),
                testUser.getFirstName(),
                testUser.getLastName(),
                new StandardUserResponseDTO(
                        testUser.getId(),
                        testUser.getFirstName(),
                        testUser.getLastName(),
                        testUser.getEmail().getValue(),
                        testUser.getAge(),
                        List.of(),
                        testUser.getPhoneNumber().getValue()
                ),
                testPost.getTitle(),
                testPost.getDescription(),
                new LocationDTO(12.5, 55.6, "Denmark", "Copenhagen", "Formatted Address"),
                testPost.getCategories(),
                testPost.getAgeFrom(),
                testPost.getAgeTo(),
                testPost.getPhotoUrl()
        );

        testAuthentication = mock(Authentication.class);
        lenient().when(testAuthentication.getName()).thenReturn("jane@example.com");
    }

    // ─── createPost ───────────────────────────────────────────────────────────

    @Test
    void createPost_returnsMappedDTO() {
        PostCreateDTO createDTO = new PostCreateDTO(
                "Test Post",
                "Test Description",
                new LocationDTO(12.5, 55.6, "Denmark", "Copenhagen", "Formatted Address"),
                List.of(new Category("tech", "Tech", null, null)),
                18,
                30,
                "http://example.com/photo.jpg"
        );

        when(userRepository.findByEmail("jane@example.com")).thenReturn(Optional.of(testUser));
        when(locationService.createLocation(55.6, 12.5)).thenReturn(testLocation);
        when(postRepository.save(any(Post.class))).thenReturn(testPost);
        when(postMapper.toDTO(testPost)).thenReturn(testPostResponseDTO);

        PostResponseDTO result = postService.createPost(createDTO, testAuthentication);

        assertEquals(testPostResponseDTO, result);
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void createPost_throwsUsernameNotFoundException_whenUserNotFound() {
        PostCreateDTO createDTO = new PostCreateDTO(
                "Test Post",
                "Test Description",
                new LocationDTO(12.5, 55.6, "Denmark", "Copenhagen", "Formatted Address"),
                List.of(new Category("tech", "Tech", null, null)),
                18,
                30,
                "http://example.com/photo.jpg"
        );

        when(userRepository.findByEmail("jane@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> postService.createPost(createDTO, testAuthentication));
    }

    @Test
    void createPost_setsAllFieldsFromDTO() {
        PostCreateDTO createDTO = new PostCreateDTO(
                "New Title",
                "New Description",
                new LocationDTO(12.5, 55.6, "Denmark", "Copenhagen", "Formatted Address"),
                List.of(new Category("sports", "Sports", null, null)),
                20,
                40,
                "http://example.com/new-photo.jpg"
        );

        when(userRepository.findByEmail("jane@example.com")).thenReturn(Optional.of(testUser));
        when(locationService.createLocation(55.6, 12.5)).thenReturn(testLocation);
        when(postRepository.save(any(Post.class))).thenAnswer(inv -> {
            Post savedPost = inv.getArgument(0);
            assertEquals("New Title", savedPost.getTitle());
            assertEquals("New Description", savedPost.getDescription());
            assertEquals(20, savedPost.getAgeFrom());
            assertEquals(40, savedPost.getAgeTo());
            assertEquals("http://example.com/new-photo.jpg", savedPost.getPhotoUrl());
            assertEquals(testUser, savedPost.getCreatedBy());
            assertEquals(testLocation, savedPost.getLocation());
            return savedPost;
        });
        when(postMapper.toDTO(any(Post.class))).thenReturn(testPostResponseDTO);

        postService.createPost(createDTO, testAuthentication);
    }

    // ─── deletePost ───────────────────────────────────────────────────────────

    @Test
    void deletePost_deletesPost_whenExists() {
        UUID postId = UUID.randomUUID();
        when(postRepository.existsById(postId)).thenReturn(true);

        postService.deletePost(postId);

        verify(postRepository).deleteById(postId);
    }

    @Test
    void deletePost_throwsPostNotFoundException_whenNotExists() {
        UUID postId = UUID.randomUUID();
        when(postRepository.existsById(postId)).thenReturn(false);

        assertThrows(PostNotFoundException.class,
                () -> postService.deletePost(postId));

        verify(postRepository, never()).deleteById(any());
    }

    @Test
    void deletePost_exceptionMessage_containsId() {
        UUID postId = UUID.randomUUID();
        when(postRepository.existsById(postId)).thenReturn(false);

        PostNotFoundException ex = assertThrows(PostNotFoundException.class,
                () -> postService.deletePost(postId));

        assertTrue(ex.getMessage().contains(postId.toString()));
    }

    // ─── getPostById ──────────────────────────────────────────────────────────

    @Test
    void getPostById_returnsMappedDTO_whenPostExists() {
        UUID postId = testPost.getId();
        when(postRepository.findById(postId)).thenReturn(Optional.of(testPost));
        when(postMapper.toDTO(testPost)).thenReturn(testPostResponseDTO);

        PostResponseDTO result = postService.getPostById(postId);

        assertEquals(testPostResponseDTO, result);
    }

    @Test
    void getPostById_throwsPostNotFoundException_whenPostNotFound() {
        UUID postId = UUID.randomUUID();
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class,
                () -> postService.getPostById(postId));
    }

    // ─── getOwnPosts ─────────────────────────────────────────────────────────

    @Test
    void getOwnPosts_returnsMappedDTOs() {
        when(authService.getUserFromAuth(testAuthentication)).thenReturn(testUser);
        when(postRepository.findAllByUserId(testUser.getId())).thenReturn(List.of(testPost));
        when(postMapper.toDTO(testPost)).thenReturn(testPostResponseDTO);

        List<PostResponseDTO> result = postService.getOwnPosts(testAuthentication);

        assertEquals(1, result.size());
        assertEquals(testPostResponseDTO, result.get(0));
    }

    @Test
    void getOwnPosts_returnsEmptyList_whenNoPosts() {
        when(authService.getUserFromAuth(testAuthentication)).thenReturn(testUser);
        when(postRepository.findAllByUserId(testUser.getId())).thenReturn(List.of());

        List<PostResponseDTO> result = postService.getOwnPosts(testAuthentication);

        assertTrue(result.isEmpty());
    }

    @Test
    void getOwnPosts_delegatesToAuthService() {
        when(authService.getUserFromAuth(testAuthentication)).thenReturn(testUser);
        when(postRepository.findAllByUserId(testUser.getId())).thenReturn(List.of());

        postService.getOwnPosts(testAuthentication);

        verify(authService).getUserFromAuth(testAuthentication);
    }

    // ─── getFeed ─────────────────────────────────────────────────────────────

    @Test
    void getFeed_returnsRankedAndMappedPosts() {
        LocationDTO locationDTO = new LocationDTO(12.5, 55.6, "Denmark", "Copenhagen", "Formatted Address");
        List<Post> rawPosts = List.of(testPost);
        List<Post> rankedPosts = List.of(testPost);

        when(authService.getUserFromAuth(testAuthentication)).thenReturn(testUser);
        when(postRepository.filterByAgeAndLocation(eq(testUser.getId()), eq(testUser.getAge()), eq(12.5), eq(55.6)))
                .thenReturn(rawPosts);
        when(feedRankingService.rankFeed(eq(testUser), eq(rawPosts), eq(locationDTO)))
                .thenReturn(rankedPosts);
        when(postMapper.toDTO(testPost)).thenReturn(testPostResponseDTO);

        List<PostResponseDTO> result = postService.getFeed(testAuthentication, locationDTO);

        assertEquals(1, result.size());
        assertEquals(testPostResponseDTO, result.get(0));
    }

    @Test
    void getFeed_returnsEmptyList_whenNoPostsMatch() {
        LocationDTO locationDTO = new LocationDTO(12.5, 55.6, "Denmark", "Copenhagen", "Formatted Address");

        when(authService.getUserFromAuth(testAuthentication)).thenReturn(testUser);
        when(postRepository.filterByAgeAndLocation(eq(testUser.getId()), eq(testUser.getAge()), eq(12.5), eq(55.6)))
                .thenReturn(List.of());

        List<PostResponseDTO> result = postService.getFeed(testAuthentication, locationDTO);

        assertTrue(result.isEmpty());
    }

    @Test
    void getFeed_delegatesToFeedRankingService() {
        LocationDTO locationDTO = new LocationDTO(12.5, 55.6, "Denmark", "Copenhagen", "Formatted Address");
        List<Post> rawPosts = List.of(testPost);

        when(authService.getUserFromAuth(testAuthentication)).thenReturn(testUser);
        when(postRepository.filterByAgeAndLocation(eq(testUser.getId()), eq(testUser.getAge()), eq(12.5), eq(55.6)))
                .thenReturn(rawPosts);
        when(feedRankingService.rankFeed(eq(testUser), eq(rawPosts), eq(locationDTO)))
                .thenReturn(List.of(testPost));
        when(postMapper.toDTO(testPost)).thenReturn(testPostResponseDTO);

        postService.getFeed(testAuthentication, locationDTO);

        verify(feedRankingService).rankFeed(eq(testUser), eq(rawPosts), eq(locationDTO));
    }

}
