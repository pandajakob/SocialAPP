package socialapp.backend.posts.DTO;

import socialapp.backend.Location.LocationDTO;

public record PostsWithinMetersDTO(
        LocationDTO location,
        int meters) {
}
