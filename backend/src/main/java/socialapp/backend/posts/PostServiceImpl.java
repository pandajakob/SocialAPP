package socialapp.backend.posts;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialapp.backend.Location.Location;
import socialapp.backend.Location.LocationService;
import socialapp.backend.Location.LocationDTO;
import socialapp.backend.posts.DTO.PostCreateDTO;
import socialapp.backend.posts.DTO.PostResponseDTO;
import socialapp.backend.posts.DTO.PostsWithinMetersDTO;
import socialapp.backend.posts.exceptions.PostNotFoundException;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.users.User;
import socialapp.backend.users.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    PostRepository postRepository;
    UserRepository userRepository;
    LocationService locationService;
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, LocationService locationService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.locationService = locationService;

    }

    public PostResponseDTO createPost(PostCreateDTO postCreateDTO) {
        Post post = new Post();
        post.setCategories(postCreateDTO.categories());
        post.setTitle(postCreateDTO.title());
        post.setAgeFrom(postCreateDTO.ageFrom());
        post.setAgeTo(postCreateDTO.ageTo());
        post.setPhotoUrl(postCreateDTO.photoURL());
        post.setDescription(postCreateDTO.description());
        post.setLocation(locationService.createLocation(postCreateDTO.location().latitude(), postCreateDTO.location().longitude()));
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

    @Override
    public List<PostResponseDTO> getOwnPosts(Authentication authentication) {
        Email email = new Email(authentication.getName());
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            List<Post> posts = postRepository.findAllByUserId(user.get().getId());
            return posts.stream().map(this::convertPostResponseDTO).toList();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
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
        Location location = post.getLocation();
        LocationDTO locationDTO = new LocationDTO(
                location.getCoordinates().getX(),
                location.getCoordinates().getY(),
                location.getCountry(),
                location.getCity(),
                location.getFormattedAddress());

        return new PostResponseDTO(
                post.getId(),
                post.getDate(),
                post.getCreatedBy().getFirstName(),
                post.getCreatedBy().getLastName(),
                post.getCreatedBy().getId().toString(),
                post.getTitle(),
                post.getDescription(),
                locationDTO,
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
