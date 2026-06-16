package socialapp.backend.posts;

import jdk.jshell.spi.ExecutionControl;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Location;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import socialapp.backend.posts.DTO.LocationDTO;
import socialapp.backend.posts.DTO.PostCreateDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    PostRepository postRepository;

    public PostService(PostRepository postRepository) {
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

        postRepository.save(post);

        return convertPostResponseDTO(post);
    }

    public List<PostResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
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
