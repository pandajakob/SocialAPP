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
import socialapp.backend.categories.Category;
import socialapp.backend.posts.DTO.*;
import socialapp.backend.posts.exceptions.PostNotFoundException;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.users.DTO.StandardUserResponseDTO;
import socialapp.backend.users.User;
import socialapp.backend.users.UserRepository;

import java.util.*;

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

    public PostResponseDTO createPost(PostCreateDTO postCreateDTO,  Authentication authentication) {
        Post post = new Post();
        User user = userRepository.findByEmail(new Email(authentication.getName())).get();
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
        User user = getUserFromAuth(authentication);
        List<Post> posts = postRepository.findAllByUserId(user.getId());
        return posts.stream().map(this::convertPostResponseDTO).toList();
    }


    public List<PostResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDTO> postResponseDTOS = new ArrayList<>();

        for (Post post : posts) {
            postResponseDTOS.add(convertPostResponseDTO(post));
        }
        return postResponseDTOS;
    }

    public List<PostResponseDTO> getFeed(Authentication authentication, LocationDTO locationDTO) {
        User user = getUserFromAuth(authentication);

        List<Post> postWithDistances = postRepository.filterByAgeAndLocation(user.getAge(), locationDTO.longitude(), locationDTO.latitude());

        List<RankedPost> rankedPosts = new ArrayList<>();

        postWithDistances.forEach(post -> {
            if (post == null) {
                throw new RuntimeException("post is null");
            }
            double daysAgoCreated = post.getDate().compareTo(new Date());
            double distanceKm = getDistanceInKm(locationDTO, post.getLocation());
            double interestMatches = getCategoryMatches(post.getCategories(), user.getInterests());
            double distanceScore = Math.min(distanceKm, 20);

            double score = (daysAgoCreated*0.5) + (distanceScore*0.5) - (interestMatches*5);

            rankedPosts.add(new RankedPost(post, score));
        });
        rankedPosts.sort(Comparator.comparingDouble(RankedPost::score));

        List<PostResponseDTO> postResponseDTOS = new ArrayList<>();

        for (RankedPost post : rankedPosts) {
            postResponseDTOS.add(convertPostResponseDTO(post.post()));
        }
        return postResponseDTOS;
    }

    private double getDistanceInKm(LocationDTO userLocation, Location postLocation) {
        double earthRadiusKm = 6371.0;

        double userLat = Math.toRadians(userLocation.latitude());
        double userLon = Math.toRadians(userLocation.longitude());

        double postLat = Math.toRadians(postLocation.getCoordinates().getY());
        double postLon = Math.toRadians(postLocation.getCoordinates().getX());

        double deltaLat = postLat - userLat;
        double deltaLon = postLon - userLon;

        double a =
                Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                        + Math.cos(userLat) * Math.cos(postLat)
                        * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        return 2 * earthRadiusKm * Math.asin(Math.sqrt(a));
    }

    private int getCategoryMatches(List<Category> categories1, List<Category> categories2) {
        int matches = 0;
        for (Category category : categories1) {
                if (categories2.contains(category)) {
                    matches++;
                }
            }
        return matches;
    }
    public List<PostResponseDTO> getNearest(LocationDTO locationDTO) {
        List<Post> posts = postRepository.findNearest(locationDTO.latitude(),locationDTO.longitude());
        List<PostResponseDTO> postResponseDTOS = new ArrayList<>();
        for (Post post : posts) {
            postResponseDTOS.add(convertPostResponseDTO(post));
        }
        return postResponseDTOS;
    }

    private User getUserFromAuth(Authentication authentication) {
        Email email = new Email(authentication.getName());
        Optional<User> user = userRepository.findByEmail(email);
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
