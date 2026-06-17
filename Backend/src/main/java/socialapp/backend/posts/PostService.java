package socialapp.backend.posts;

import socialapp.backend.posts.DTO.LocationDTO;
import socialapp.backend.posts.DTO.PostCreateDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;
import socialapp.backend.posts.DTO.PostsWithinMetersDTO;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostResponseDTO> getAllPostsWithinMeters(PostsWithinMetersDTO postsWithinMetersDTO);

    List<PostResponseDTO> getAllPosts();

    PostResponseDTO createPost(PostCreateDTO postCreateDTO);

    void deletePost(UUID id);

    PostResponseDTO getPostById(UUID id);

    List<PostResponseDTO> getNearest(LocationDTO locationDTO);
}
