package com.example.redditclone.posts.services;

import com.example.redditclone.posts.models.Post;
import com.example.redditclone.posts.repositories.PostRepository;
import com.example.redditclone.users.models.Privilege;
import com.example.redditclone.users.models.Role;
import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.RoleRepository;
import com.example.redditclone.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class PostValidatorImp implements PostValidator{
    private PostRepository postRepository;
    private PostService postService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public PostValidatorImp(PostRepository postRepository, PostService postService, UserRepository userRepository,
                            RoleRepository roleRepository) {
        this.postRepository = postRepository;
        this.postService = postService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    private boolean validateTitle(Post post) {
        if (post.getTitle().length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title must be at least 8 characters long");
        }
        return true;
    }

    private boolean validateContent(Post post) {
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
        Post currentPost = postRepository.getReferenceById(id);
        User user = userRepository.findByUsername(username);
        User owner = userRepository.findByUsername(currentPost.getOwner());
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Role moderatorRole = roleRepository.findByName("ROLE_MODERATOR");

        boolean isTitleValid = validateTitle(post);
        boolean isContentValid = validateContent(post);
        boolean isUserVerified = postService.checkEmailVerifiedAt(post.getOwner());
        boolean isUserAlsoOwner = owner.equals(user);
        boolean hasUserRole = user.getRoles().contains(adminRole) || user.getRoles().contains(moderatorRole);

        if (!isUserAlsoOwner && !hasUserRole) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User can't modify this Post!");
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
