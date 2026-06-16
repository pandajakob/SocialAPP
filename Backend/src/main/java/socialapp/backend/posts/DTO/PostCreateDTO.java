package socialapp.backend.posts.DTO;

import socialapp.backend.categories.Category;


import java.util.List;

public record CreatePostDTO(String title, String Description, List<Category> categories, int ageFrom, int ageTo, String photoURL) {}
