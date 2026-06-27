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
    String phoneNumber,
    String email,
    String password,
    User.Role role
)
{}
