package socialapp.backend.posts.DTO;

import socialapp.backend.posts.Post;

public record RankedPost(
        Post post,
        double score
) {}