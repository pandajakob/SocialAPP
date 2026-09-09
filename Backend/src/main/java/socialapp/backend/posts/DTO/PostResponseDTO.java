package socialapp.backend.posts.DTO;

import socialapp.backend.location.LocationDTO;
import socialapp.backend.categories.Category;
import socialapp.backend.users.DTO.StandardUserResponseDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record PostResponseDTO(
        UUID id,
        Date date,
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
