package socialapp.backend.posts;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import socialapp.backend.posts.DTO.PostsWithinMetersDTO;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    @Query(value = """
    SELECT *
    FROM posts p
    WHERE ST_DWithin(
        p.location,
        ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography,
        :meters
    )
    """, nativeQuery = true)
    List<Post> getAllPostsWithinMeters(double longitude, double latitude, int meters);

    @Query(value = """
    SELECT *
    FROM posts p
    ORDER BY ST_Distance(
        p.location,
        ST_SetSRID(
            ST_MakePoint(:longitude, :latitude), 4326)::geography
        )
    """, nativeQuery = true)
    List<Post> findNearest(double longitude, double latitude);
}
