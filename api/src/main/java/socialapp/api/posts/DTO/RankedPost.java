package socialapp.api.posts.DTO;

import socialapp.api.posts.Post;

public record RankedPost(
        Post post,
        double score
) {}