package socialapp.api.posts.DTO;

import socialapp.api.location.LocationDTO;
import socialapp.api.categories.Category;


import java.util.List;

public record PostCreateDTO(
        String title,
        String description,
        LocationDTO location,
        List<Category> categories,
        int ageFrom,
        int ageTo,
        String photoURL) {}
