package socialapp.api.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import socialapp.api.categories.Category;
import socialapp.api.shared.domain_primitives.Email;
import socialapp.api.shared.domain_primitives.EncodedPassword;
import socialapp.api.shared.domain_primitives.PhoneNumber;
import socialapp.api.users.DTO.StandardUserResponseDTO;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void toDTO_mapsIdCorrectly() {
        User user = buildUser("jane@example.com", "+4512345678");
        UUID id = UUID.randomUUID();
        user.setId(id);

        StandardUserResponseDTO dto = userMapper.toDTO(user);

        assertEquals(id, dto.id());
    }

    @Test
    void toDTO_mapsFirstNameCorrectly() {
        User user = buildUser("jane@example.com", "+4512345678");

        StandardUserResponseDTO dto = userMapper.toDTO(user);

        assertEquals("Jane", dto.firstName());
    }

    @Test
    void toDTO_mapsLastNameCorrectly() {
        User user = buildUser("jane@example.com", "+4512345678");

        StandardUserResponseDTO dto = userMapper.toDTO(user);

        assertEquals("Doe", dto.lastName());
    }

    @Test
    void toDTO_mapsEmailAsRawString() {
        User user = buildUser("jane@example.com", "+4512345678");

        StandardUserResponseDTO dto = userMapper.toDTO(user);

        assertEquals("jane@example.com", dto.email());
    }

    @Test
    void toDTO_mapsPhoneNumberAsRawString() {
        User user = buildUser("jane@example.com", "+4512345678");

        StandardUserResponseDTO dto = userMapper.toDTO(user);

        assertEquals("+4512345678", dto.phoneNumber());
    }

    @Test
    void toDTO_mapsAgeCorrectly() {
        User user = buildUser("jane@example.com", "+4512345678");

        StandardUserResponseDTO dto = userMapper.toDTO(user);

        assertEquals(28, dto.age());
    }

    @Test
    void toDTO_mapsEmptyInterestsList() {
        User user = buildUser("jane@example.com", "+4512345678");
        user.setInterests(List.of());

        StandardUserResponseDTO dto = userMapper.toDTO(user);

        assertNotNull(dto.interests());
        assertTrue(dto.interests().isEmpty());
    }

    @Test
    void toDTO_mapsNonEmptyInterestsList() {
        User user = buildUser("jane@example.com", "+4512345678");
        Category cat = new Category("tech", "Tech", null, null);
        user.setInterests(List.of(cat));

        StandardUserResponseDTO dto = userMapper.toDTO(user);

        assertEquals(1, dto.interests().size());
        assertEquals(cat, dto.interests().get(0));
    }

    @Test
    void toDTO_returnsDistinctDTOForEachUser() {
        User user1 = buildUser("alice@example.com", "+4511111111");
        user1.setId(UUID.randomUUID());
        user1.setInterests(List.of());

        User user2 = buildUser("bob@example.com", "+4522222222");
        user2.setId(UUID.randomUUID());
        user2.setInterests(List.of());

        StandardUserResponseDTO dto1 = userMapper.toDTO(user1);
        StandardUserResponseDTO dto2 = userMapper.toDTO(user2);

        assertNotEquals(dto1.email(), dto2.email());
        assertNotEquals(dto1.phoneNumber(), dto2.phoneNumber());
        assertNotEquals(dto1.id(), dto2.id());
    }

    private User buildUser(String email, String phone) {
        User user = new User(
                "Jane",
                "Doe",
                28,
                new EncodedPassword("hashed-pw"),
                new Email(email),
                new PhoneNumber(phone)
        );
        user.setId(UUID.randomUUID());
        user.setInterests(List.of());
        user.setSavedPosts(List.of());
        return user;
    }
}
