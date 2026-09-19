package socialapp.api.posts.DTO;

import socialapp.api.location.LocationDTO;

public record PostsWithinMetersDTO(
        LocationDTO location,
        int meters) {
}
