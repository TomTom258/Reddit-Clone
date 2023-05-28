package com.example.redditclone.posts.services;

import com.example.redditclone.comments.models.Comment;
import com.example.redditclone.posts.models.Post;
import com.example.redditclone.users.models.User;
import com.example.redditclone.posts.repositories.PostRepository;
import com.example.redditclone.users.repositories.UserRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class PostServiceImp implements PostService{
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public PostServiceImp(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean upvotePost(long id) {
        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post doesn't exists!");
        }

        Post upvotedPost = postRepository.getReferenceById(id);
        User upvotedUser = userRepository.findByUsername(upvotedPost.getOwner());

        if (Objects.isNull(upvotedUser)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exists!");
        } else {
            upvotedPost.setReputation(upvotedPost.getReputation() + 1);
            upvotedUser.setKarma(upvotedUser.getKarma() + 1);
            postRepository.save(upvotedPost);
            userRepository.save(upvotedUser);
            return true;
        }
    }

    @Override
    public boolean downvotePost(long id) {
        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post doesn't exists!");
        }

        Post downvotedPost = postRepository.getReferenceById(id);
        User downvotedUser = userRepository.findByUsername(downvotedPost.getOwner());

        if (Objects.isNull(downvotedUser)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exists!");
        } else {
            downvotedPost.setReputation(downvotedPost.getReputation() - 1);
            downvotedUser.setKarma(downvotedUser.getKarma() - 1);
            postRepository.save(downvotedPost);
            userRepository.save(downvotedUser);
            return true;
        }
    }

    @Override
    public boolean deletePost(long id) {
        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post doesn't exists!");
        }

        Post deletedPost = postRepository.getReferenceById(id);
        User user = userRepository.findByUsername(deletedPost.getOwner());

        if (Objects.isNull(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exists!");
        } else {
            postRepository.delete(deletedPost);
            return true;
        }
    }

    @Override
    public List<Post> assignProfilePictures() throws IOException {
        List<Post> storedPosts = postRepository.findAll();

        for (Post post : storedPosts) {
            User owner = userRepository.findByUsername(post.getOwner());

            byte[] fileContent = FileUtils.readFileToByteArray(new File(owner.getProfilePictureFilePath()));
            String encodedProfilePicture = Base64.getEncoder().encodeToString(fileContent);
            post.setOwnerProfilePicture(encodedProfilePicture);
            Set<Comment> commentsList = post.getComments();

            for (Comment comment : commentsList) {
                User commentOwner = userRepository.findByUsername(comment.getOwner());

                byte[] fileContentInComment = FileUtils.readFileToByteArray(new File(commentOwner.getProfilePictureFilePath()));
                String encodedProfilePictureInComment = Base64.getEncoder().encodeToString(fileContentInComment);
                comment.setOwnerProfilePicture(encodedProfilePictureInComment);
            }
        }
        return storedPosts;
    }
}
