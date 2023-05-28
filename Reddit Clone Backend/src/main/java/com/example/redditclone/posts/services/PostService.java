package com.example.redditclone.posts.services;

import com.example.redditclone.posts.models.Post;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface PostService {
    public boolean upvotePost(long id);

    public boolean downvotePost(long id);

    public boolean deletePost(long id);

    public List<Post> assignProfilePictures() throws IOException;
}
