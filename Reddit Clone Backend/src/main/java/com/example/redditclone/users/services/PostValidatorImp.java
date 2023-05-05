package com.example.redditclone.users.services;

import com.example.redditclone.users.models.Post;
import com.example.redditclone.users.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PostValidatorImp implements PostValidator{

    private PostRepository postRepository;

    @Autowired
    public PostValidatorImp(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public boolean validateTitle(Post post) {
        if (post.getTitle().length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title must be at least 8 characters long");
        }
        return true;
    }

    @Override
    public boolean validateContent(Post post) {
        if (post.getTitle().length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The content must be at least 8 characters long");
        }
        return true;
    }

    @Override
    public boolean postThePost(Post post) {
        boolean isTitleValid = validateTitle(post);
        boolean isContentValid = validateContent(post);
        if (isTitleValid && isContentValid) {
            Post newPost = new Post(post.getTitle(), post.getContent(), "testOwner");
            postRepository.save(newPost);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown error");
        }
    }
}
