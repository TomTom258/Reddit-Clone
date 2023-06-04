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
    public boolean editThePost(Post post, Long id, String username) {
        boolean isTitleValid = validateTitle(post);
        boolean isContentValid = validateContent(post);
        boolean isUserVerified = postService.checkEmailVerifiedAt(post.getOwner());
        Post currentPost = postRepository.getReferenceById(id);

        if (!currentPost.getOwner().equals(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User isn't owner of the Post!");
        }

        if (isTitleValid && isContentValid && isUserVerified) {
            currentPost.setTitle(post.getTitle());
            currentPost.setContent(post.getContent());
            currentPost.setCreated_at(LocalDateTime.now());
            postRepository.save(currentPost);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown error");
        }
    }
}
