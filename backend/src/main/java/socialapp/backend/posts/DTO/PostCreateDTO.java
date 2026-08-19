package socialapp.backend.posts.DTO;

import socialapp.backend.categories.Category;


import java.util.List;

public record PostCreateDTO(
        String title,
        String description,
        LocationDTO location,
        List<Category> categories,
        int ageFrom,
        int ageTo,
        String photoURL) {}
