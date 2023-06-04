package com.example.redditclone.posts.services;

import com.example.redditclone.posts.models.Post;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public interface PostService {
    public boolean checkEmailVerifiedAt(String username);
    public boolean upvotePost(long id, String upvotedByUsername);

    public boolean downvotePost(long id, String downvotedByUsername);

    public boolean deletePost(long id, String username);

    public List<Post> mapProfilePicturesAndReactions(String username) throws IOException;
}
