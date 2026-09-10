package socialapp.backend.chats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import socialapp.backend.posts.Post;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    @Query(value = """
    SELECT *
    FROM chats c
    WHERE id = :userId
    """, nativeQuery = true)
    List<Chat> findAllByUserId(UUID userId);

}
