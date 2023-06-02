package com.example.redditclone.posts.services;

import com.example.redditclone.posts.models.Post;
import com.example.redditclone.posts.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class PostValidatorImp implements PostValidator{

    private PostRepository postRepository;
    private PostService postService;

    @Autowired
    public PostValidatorImp(PostRepository postRepository, PostService postService) {
        this.postRepository = postRepository;
        this.postService = postService;
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
        if (post.getContent().length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The content must be at least 8 characters long");
        }
        return true;
    }

    @Override
    public boolean postThePost(Post post) {
        boolean isTitleValid = validateTitle(post);
        boolean isContentValid = validateContent(post);
        boolean isUserVerified = postService.checkEmailVerifiedAt(post.getOwner());

        if (isTitleValid && isContentValid && isUserVerified) {
            Post newPost = new Post(post.getTitle(), post.getContent(), post.getOwner());
            postRepository.save(newPost);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown error");
        }
    }

    @Override
    public boolean editThePost(Post post, Long id) {
        boolean isTitleValid = validateTitle(post);
        boolean isContentValid = validateContent(post);
        boolean isUserVerified = postService.checkEmailVerifiedAt(post.getOwner());

        if (isTitleValid && isContentValid && isUserVerified) {
            Post oldPost = postRepository.getReferenceById(id);
            oldPost.setTitle(post.getTitle());
            oldPost.setContent(post.getContent());
            oldPost.setCreated_at(LocalDateTime.now());
            postRepository.save(oldPost);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown error");
        }
    }
}
