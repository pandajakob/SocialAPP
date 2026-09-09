package socialapp.backend.posts.DTO;

import socialapp.backend.location.LocationDTO;

public record PostsWithinMetersDTO(
        LocationDTO location,
        int meters) {
}
