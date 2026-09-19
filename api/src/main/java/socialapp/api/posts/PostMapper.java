package socialapp.api.posts;

import org.springframework.stereotype.Component;
import socialapp.api.location.Location;
import socialapp.api.location.LocationDTO;
import socialapp.api.posts.DTO.PostResponseDTO;
import socialapp.api.users.DTO.StandardUserResponseDTO;
import socialapp.api.users.UserMapper;

@Component
public class PostMapper {
    UserMapper userMapper;

    public PostMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public PostResponseDTO toDTO(Post post) {
        Location location = post.getLocation();
        LocationDTO locationDTO = new LocationDTO(
                location.getCoordinates().getX(),
                location.getCoordinates().getY(),
                location.getCountry(),
                location.getCity(),
                location.getFormattedAddress());

        StandardUserResponseDTO userResponseDTO = userMapper.toDTO(post.getCreatedBy());

        return new PostResponseDTO(
                post.getId(),
                post.getCreatedAt(),
                post.getCreatedBy().getFirstName(),
                post.getCreatedBy().getLastName(),
                userResponseDTO,
                post.getTitle(),
                post.getDescription(),
                locationDTO,
                post.getCategories(),
                post.getAgeFrom(),
                post.getAgeTo(),
                post.getPhotoUrl());
    }
}
