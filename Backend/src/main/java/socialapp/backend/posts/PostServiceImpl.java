package socialapp.backend.posts;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import socialapp.backend.posts.DTO.LocationDTO;
import socialapp.backend.posts.DTO.PostCreateDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;
import socialapp.backend.posts.DTO.PostsWithinMetersDTO;
import socialapp.backend.posts.exceptions.PostNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponseDTO createPost(PostCreateDTO postCreateDTO) {
        Post post = new Post();
        post.setCategories(postCreateDTO.categories());
        post.setTitle(postCreateDTO.title());
        post.setAgeFrom(postCreateDTO.ageFrom());
        post.setAgeTo(postCreateDTO.ageTo());
        post.setPhotoUrl(postCreateDTO.photoURL());
        post.setDescription(postCreateDTO.description());
        post.setLocation(extractLocationPoint(postCreateDTO.location()));

        Post newPost = postRepository.save(post);

        return convertPostResponseDTO(newPost);
    }

    @Override
    public void deletePost(UUID id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException(id);
        }
        postRepository.deleteById(id);
    }

    @Override
    public PostResponseDTO getPostById(UUID id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        return convertPostResponseDTO(post);
    }

    @Override
    public List<PostResponseDTO> getAllPostsWithinMeters(PostsWithinMetersDTO postsWithinMetersDTO) {

        List<Post> posts = postRepository.getAllPostsWithinMeters(
                postsWithinMetersDTO.location().longitude(),
                postsWithinMetersDTO.location().latitude(),
                postsWithinMetersDTO.meters());
        List<PostResponseDTO> postResponseDTOS = new ArrayList<>();

        for (Post post : posts) {
            postResponseDTOS.add(convertPostResponseDTO(post));
        }
        return postResponseDTOS;
    }


    public List<PostResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDTO> postResponseDTOS = new ArrayList<>();

        for (Post post : posts) {
            postResponseDTOS.add(convertPostResponseDTO(post));
        }
        return postResponseDTOS;

    }

    public List<PostResponseDTO> getNearest(LocationDTO locationDTO) {
        List<Post> posts = postRepository.findNearest(locationDTO.latitude(),locationDTO.longitude());
        List<PostResponseDTO> postResponseDTOS = new ArrayList<>();

        for (Post post : posts) {
            postResponseDTOS.add(convertPostResponseDTO(post));
        }
        return postResponseDTOS;

    }
    
    private PostResponseDTO convertPostResponseDTO(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getDate(),
                post.getTitle(),
                post.getDescription(),
                new LocationDTO(post.getLocation().getX(), post.getLocation().getY()),
                post.getCategories(),
                post.getAgeFrom(),
                post.getAgeTo(),
                post.getPhotoUrl());
    }

    private Point extractLocationPoint(LocationDTO locationDTO) {
        GeometryFactory factory = new GeometryFactory();
        return factory.createPoint(new Coordinate(locationDTO.longitude(), locationDTO.latitude()));
    }

}
