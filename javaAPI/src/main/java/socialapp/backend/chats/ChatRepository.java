package socialapp.backend.chats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import socialapp.backend.posts.Post;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    @Query("""
    SELECT c
    FROM Chat c
    JOIN c.participants p
    WHERE p.id = :userId
    """)
    List<Chat> findAllByUserId(UUID userId);
}
