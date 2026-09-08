package socialapp.backend.authentication;

public record RegisterDTO(
    String firstName,
    String lastName,
    Integer age,
    String phoneNumber,
    String email,
    String password
)
{}
