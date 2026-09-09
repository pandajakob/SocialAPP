package socialapp.backend.posts;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialapp.backend.location.LocationService;
import socialapp.backend.location.LocationDTO;
import socialapp.backend.authentication.AuthService;
import socialapp.backend.feed.FeedRankingService;
import socialapp.backend.posts.DTO.*;
import socialapp.backend.posts.exceptions.PostNotFoundException;
import socialapp.backend.users.User;
import socialapp.backend.users.UserRepository;

import java.util.*;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LocationService locationService;
    private final FeedRankingService feedRankingService;
    private final AuthService authService;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, UserRepository userRepository, LocationService locationService, FeedRankingService feedRankingService, AuthService authService, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.locationService = locationService;
        this.feedRankingService = feedRankingService;
        this.authService = authService;
        this.postMapper = postMapper;
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

        return postMapper.toDTO(newPost);
    }

    private User unpackUser(Optional<User> optionalUser) {
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public void deletePost(UUID id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException(id);
        }
        postRepository.deleteById(id);
    }

    public PostResponseDTO getPostById(UUID id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        return postMapper.toDTO(post);
    }

    public List<PostResponseDTO> getOwnPosts(Authentication authentication) {
        User user = authService.getUserFromAuth(authentication);
        List<Post> posts = postRepository.findAllByUserId(user.getId());
        return posts.stream().map(postMapper::toDTO).toList();
    }

    public List<PostResponseDTO> getFeed(Authentication authentication, LocationDTO locationDTO) {
        User user = authService.getUserFromAuth(authentication);

        List<Post> posts = postRepository.filterByAgeAndLocation(user.getAge(), locationDTO.longitude(), locationDTO.latitude());

        List<Post> feed = feedRankingService.rankFeed(user, posts, locationDTO);

        List<PostResponseDTO> postResponseDTOS = new ArrayList<>();

        for (Post post : feed) {
            postResponseDTOS.add(postMapper.toDTO(post));
        }
        return postResponseDTOS;
    }


}
