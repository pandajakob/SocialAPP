package socialapp.backend.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query(value = """
    SELECT *
    FROM posts p
    WHERE user_id = :userId
    """, nativeQuery = true)
    List<Post> findAllByUserId(UUID userId);

    @Query(value = """
    SELECT p.*
    FROM posts p
    JOIN locations l ON p.location_id = l.id
    WHERE p.age_to >= :age
      AND p.age_from <= :age
      and p.user_id != :userId
    ORDER BY ST_Distance(
        l.coordinates,
        ST_SetSRID(
            ST_MakePoint(:longitude, :latitude), 4326
        )::geography
    )
    """, nativeQuery = true)
    List<Post> filterByAgeAndLocation(UUID userId, int age, double longitude, double latitude);

}
