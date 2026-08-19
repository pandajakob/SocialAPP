package socialapp.backend.posts.DTO;

import socialapp.backend.categories.Category;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record PostResponseDTO(
        UUID id,
        Date date,
        String userFirstName,
        String userLastName,
        String userId,
        String title,
        String Description,
        LocationDTO location,
        List<Category> categories,
        int ageFrom,
        int ageTo,
        String photoURL) {}
