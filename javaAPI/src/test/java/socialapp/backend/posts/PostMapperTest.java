package socialapp.backend.posts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import socialapp.backend.categories.Category;
import socialapp.backend.location.Location;
import socialapp.backend.location.LocationDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.EncodedPassword;
import socialapp.backend.shared.domain_primitives.PhoneNumber;
import socialapp.backend.users.User;
import socialapp.backend.users.UserMapper;
import socialapp.backend.users.DTO.StandardUserResponseDTO;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostMapperTest {

    private PostMapper postMapper;

    @Mock
    private UserMapper userMapper;

    private User testUser;
    private Location testLocation;
    private Post testPost;
    private GeometryFactory geometryFactory;
    private StandardUserResponseDTO testUserDTO;

    @BeforeEach
    void setUp() {
        postMapper = new PostMapper(userMapper);
        geometryFactory = new GeometryFactory();

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
        testLocation.setCoordinates(geometryFactory.createPoint(new Coordinate(12.5, 55.6)));
        testLocation.setCountry("Denmark");
        testLocation.setCity("Copenhagen");
        testLocation.setFormattedAddress("Formatted Address");

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

        testUserDTO = new StandardUserResponseDTO(
                testUser.getId(),
                testUser.getFirstName(),
                testUser.getLastName(),
                testUser.getEmail().getValue(),
                testUser.getAge(),
                List.of(),
                testUser.getPhoneNumber().getValue()
        );

        when(userMapper.toDTO(testUser)).thenReturn(testUserDTO);
    }

    // ─── toDTO ────────────────────────────────────────────────────────────────

    @Test
    void toDTO_mapsIdCorrectly() {
        PostResponseDTO dto = postMapper.toDTO(testPost);

        assertEquals(testPost.getId(), dto.id());
    }

    @Test
    void toDTO_mapsCreatedAtCorrectly() {
        PostResponseDTO dto = postMapper.toDTO(testPost);

        assertEquals(testPost.getCreatedAt(), dto.createdAt());
    }

    @Test
    void toDTO_mapsUserFirstNameCorrectly() {
        PostResponseDTO dto = postMapper.toDTO(testPost);

        assertEquals(testUser.getFirstName(), dto.userFirstName());
    }

    @Test
    void toDTO_mapsUserLastNameCorrectly() {
        PostResponseDTO dto = postMapper.toDTO(testPost);

        assertEquals(testUser.getLastName(), dto.userLastName());
    }

    @Test
    void toDTO_mapsUserCorrectly() {
        PostResponseDTO dto = postMapper.toDTO(testPost);

        assertEquals(testUserDTO, dto.user());
        verify(userMapper).toDTO(testUser);
    }

    @Test
    void toDTO_mapsTitleCorrectly() {
        PostResponseDTO dto = postMapper.toDTO(testPost);

        assertEquals(testPost.getTitle(), dto.title());
    }

    @Test
    void toDTO_mapsDescriptionCorrectly() {
        PostResponseDTO dto = postMapper.toDTO(testPost);

        assertEquals(testPost.getDescription(), dto.Description());
    }

    @Test
    void toDTO_mapsLocationCorrectly() {
        PostResponseDTO dto = postMapper.toDTO(testPost);

        assertNotNull(dto.location());
        assertEquals(testLocation.getCoordinates().getX(), dto.location().longitude());
        assertEquals(testLocation.getCoordinates().getY(), dto.location().latitude());
        assertEquals(testLocation.getCountry(), dto.location().country());
        assertEquals(testLocation.getCity(), dto.location().city());
        assertEquals(testLocation.getFormattedAddress(), dto.location().formattedAddress());
    }

    @Test
    void toDTO_mapsCategoriesCorrectly() {
        PostResponseDTO dto = postMapper.toDTO(testPost);

        assertEquals(testPost.getCategories(), dto.categories());
    }

    @Test
    void toDTO_mapsAgeFromCorrectly() {
        PostResponseDTO dto = postMapper.toDTO(testPost);

        assertEquals(testPost.getAgeFrom(), dto.ageFrom());
    }

    @Test
    void toDTO_mapsAgeToCorrectly() {
        PostResponseDTO dto = postMapper.toDTO(testPost);

        assertEquals(testPost.getAgeTo(), dto.ageTo());
    }

    @Test
    void toDTO_mapsPhotoUrlCorrectly() {
        PostResponseDTO dto = postMapper.toDTO(testPost);

        assertEquals(testPost.getPhotoUrl(), dto.photoURL());
    }

    @Test
    void toDTO_returnsDistinctDTOForEachPost() {
        Post secondPost = new Post();
        secondPost.setId(UUID.randomUUID());
        secondPost.setTitle("Second Post");
        secondPost.setDescription("Second Description");
        secondPost.setAgeFrom(20);
        secondPost.setAgeTo(40);
        secondPost.setPhotoUrl("http://example.com/second.jpg");
        secondPost.setCategories(List.of());

        User secondUser = new User(
                "John",
                "Smith",
                35,
                new EncodedPassword("pw2"),
                new Email("john@example.com"),
                new PhoneNumber("+4587654321")
        );
        secondUser.setId(UUID.randomUUID());
        secondUser.setInterests(List.of());
        secondUser.setSavedPosts(List.of());
        secondPost.setCreatedBy(secondUser);
        secondPost.setLocation(testLocation);

        StandardUserResponseDTO secondUserDTO = new StandardUserResponseDTO(
                secondUser.getId(),
                secondUser.getFirstName(),
                secondUser.getLastName(),
                secondUser.getEmail().getValue(),
                secondUser.getAge(),
                List.of(),
                secondUser.getPhoneNumber().getValue()
        );
        when(userMapper.toDTO(secondUser)).thenReturn(secondUserDTO);

        PostResponseDTO dto1 = postMapper.toDTO(testPost);
        PostResponseDTO dto2 = postMapper.toDTO(secondPost);

        assertNotEquals(dto1.id(), dto2.id());
        assertNotEquals(dto1.title(), dto2.title());
    }
}
