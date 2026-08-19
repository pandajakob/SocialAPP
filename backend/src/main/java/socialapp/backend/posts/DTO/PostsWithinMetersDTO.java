package socialapp.backend.posts.DTO;

public record PostsWithinMetersDTO(
        LocationDTO location,
        int meters) {
}
