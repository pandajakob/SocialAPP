package socialapp.backend.users.DTO;

import socialapp.backend.categories.Category;
import socialapp.backend.shared.domain_primitives.PhotoURL;

import java.util.List;
import java.util.UUID;

public record StandardUserResponseDTO(UUID id, String firstName, String lastName, String email, Integer age,
                                      List<Category> interests, String phoneNumber) { }
