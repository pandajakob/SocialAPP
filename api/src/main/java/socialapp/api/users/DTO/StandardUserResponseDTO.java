package socialapp.api.users.DTO;

import socialapp.api.categories.Category;

import java.util.List;
import java.util.UUID;

public record StandardUserResponseDTO(UUID id, String firstName, String lastName, String email, Integer age,
                                      List<Category> interests, String phoneNumber) { }
