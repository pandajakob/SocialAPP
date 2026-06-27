package socialapp.backend.authentication;

import socialapp.backend.categories.Category;
import socialapp.backend.users.User;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.PhotoURL;

import java.util.List;
import java.util.UUID;

public record RegisterDTO(
    String firstName,
    String lastName,
    Integer age,
    List<Category> interests,
    String phoneNumber,
    String profilePhotoURL,
    String email,
    String password,
    User.Role role
)
{}
