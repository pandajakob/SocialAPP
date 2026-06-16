package socialapp.backend.posts;

import socialapp.backend.posts.DTO.PostCreateDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<PostResponseDTO> getAllPosts();

    PostResponseDTO createPost(PostCreateDTO postCreateDTO);

    void deletePost(UUID id);
}
