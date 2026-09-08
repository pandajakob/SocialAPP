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
import socialapp.backend.feed.FeedRankingService;
import socialapp.backend.posts.DTO.*;
import socialapp.backend.posts.exceptions.PostNotFoundException;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.users.DTO.StandardUserResponseDTO;
import socialapp.backend.users.User;
import socialapp.backend.users.UserRepository;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LocationService locationService;
    private final FeedRankingService feedRankingService;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, LocationService locationService, FeedRankingService feedRankingService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.locationService = locationService;
        this.feedRankingService = feedRankingService;
    }

    public PostResponseDTO createPost(PostCreateDTO postCreateDTO,  Authentication authentication) {
        Post post = new Post();
        Optional<User> fetchedUser = userRepository.findByEmail(authentication.getName());
        User user = unpackUser(fetchedUser);

        post.setCategories(postCreateDTO.categories());
        post.setCreatedBy(user);
        post.setTitle(postCreateDTO.title());
        post.setAgeFrom(postCreateDTO.ageFrom());
        post.setAgeTo(postCreateDTO.ageTo());
        post.setPhotoUrl(postCreateDTO.photoURL());
        post.setDescription(postCreateDTO.description());
        post.setLocation(locationService.createLocation(postCreateDTO.location().latitude(), postCreateDTO.location().longitude()));
        Post newPost = postRepository.save(post);

        return convertPostResponseDTO(newPost);
    }

    private User unpackUser(Optional<User> optionalUser) {
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
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
    public List<PostResponseDTO> getOwnPosts(Authentication authentication) {
        User user = getUserFromAuth(authentication);
        List<Post> posts = postRepository.findAllByUserId(user.getId());
        return posts.stream().map(this::convertPostResponseDTO).toList();
    }

    public List<PostResponseDTO> getFeed(Authentication authentication, LocationDTO locationDTO) {
        User user = getUserFromAuth(authentication);

        List<Post> posts = postRepository.filterByAgeAndLocation(user.getAge(), locationDTO.longitude(), locationDTO.latitude());

        List<Post> feed = feedRankingService.rankFeed(user, posts, locationDTO);

        List<PostResponseDTO> postResponseDTOS = new ArrayList<>();

        for (Post post : feed) {
            postResponseDTOS.add(convertPostResponseDTO(post));
        }
        return postResponseDTOS;
    }

    private User getUserFromAuth(Authentication authentication) {
        Email email = new Email(authentication.getName());
        Optional<User> user = userRepository.findByEmail(email.getValue());
        if (user.isPresent()) {
            return user.get();
        } else   {
            throw new UsernameNotFoundException("User not found");
        }
    }
    
    private PostResponseDTO convertPostResponseDTO(Post post) {
        Location location = post.getLocation();
        LocationDTO locationDTO = new LocationDTO(
                location.getCoordinates().getX(),
                location.getCoordinates().getY(),
                location.getCountry(),
                location.getCity(),
                location.getFormattedAddress());

        User user = post.getCreatedBy();
        StandardUserResponseDTO userResponseDTO = new StandardUserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail().getValue(),
                user.getAge(),
                user.getInterests(),
                user.getPhoneNumber().getValue()
        );
        return new PostResponseDTO(
                post.getId(),
                post.getDate(),
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

    private Point extractLocationPoint(LocationDTO locationDTO) {
        GeometryFactory factory = new GeometryFactory();
        return factory.createPoint(new Coordinate(locationDTO.longitude(), locationDTO.latitude()));
    }

}
