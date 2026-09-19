package socialapp.api.feed;

import socialapp.api.location.LocationDTO;
import socialapp.api.posts.Post;
import socialapp.api.users.User;

import java.util.List;

public interface FeedRanker {
    List<Post> rankFeed(User user, List<Post> posts, LocationDTO locationDTO);
}
