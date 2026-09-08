package socialapp.backend.feed;

import org.springframework.security.core.Authentication;
import socialapp.backend.Location.LocationDTO;
import socialapp.backend.posts.Post;
import socialapp.backend.users.User;

import java.util.List;

public interface FeedRanker {
    List<Post> rankFeed(User user, List<Post> posts, LocationDTO locationDTO);
}
