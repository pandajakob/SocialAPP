package socialapp.backend.users.DTO;

import socialapp.backend.categories.Category;
import socialapp.backend.shared.domain_primitives.Email;

import java.util.List;
import java.util.UUID;

public record CreateUserRequestDTO(String firstName, String lastName, String password, String email, Integer age,
                                   List<Category> interests, String phoneNumber, String profilePhotoUrl) { }

