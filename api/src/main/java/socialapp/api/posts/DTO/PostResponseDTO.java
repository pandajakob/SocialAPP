package socialapp.api.posts.DTO;

import socialapp.api.location.LocationDTO;
import socialapp.api.categories.Category;
import socialapp.api.users.DTO.StandardUserResponseDTO;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PostResponseDTO(
        UUID id,
        Instant createdAt,
        String userFirstName,
        String userLastName,
        StandardUserResponseDTO user,
        String title,
        String Description,
        LocationDTO location,
        List<Category> categories,
        int ageFrom,
        int ageTo,
        String photoURL) {}
