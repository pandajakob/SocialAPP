package socialapp.backend.feed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import socialapp.backend.Location.Location;
import socialapp.backend.Location.LocationDTO;
import socialapp.backend.categories.Category;
import socialapp.backend.posts.DTO.RankedPost;
import socialapp.backend.posts.Post;
import socialapp.backend.posts.PostRepository;
import socialapp.backend.users.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class FeedRankingService implements FeedRanker {
    @Override
    public List<Post> rankFeed(User user, List<Post> posts, LocationDTO locationDTO) {
        List<RankedPost> rankedPosts = new ArrayList<>();

        posts.forEach(post -> {
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
        return rankedPosts.stream().map(RankedPost::post).toList();
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
}
