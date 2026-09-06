package socialapp.backend.posts;

import org.springframework.security.core.Authentication;
import socialapp.backend.Location.LocationDTO;
import socialapp.backend.posts.DTO.PostCreateDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;
import socialapp.backend.posts.DTO.PostsWithinMetersDTO;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostResponseDTO> getAllPostsWithinMeters(PostsWithinMetersDTO postsWithinMetersDTO);

    List<PostResponseDTO> getOwnPosts(Authentication authentication);

    PostResponseDTO createPost(PostCreateDTO postCreateDTO,Authentication authentication);

    List<PostResponseDTO> getFeed(Authentication authentication, LocationDTO locationDTO);

    void deletePost(UUID id);

    PostResponseDTO getPostById(UUID id);

    List<PostResponseDTO> getNearest(LocationDTO locationDTO);
}
