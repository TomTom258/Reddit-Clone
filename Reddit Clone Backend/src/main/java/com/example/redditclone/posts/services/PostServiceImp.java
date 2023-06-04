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
import java.util.Objects;
import java.util.Set;

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
    public boolean checkEmailVerifiedAt(String username) {
        User user = userRepository.findByUsername(username);

        if (Objects.isNull(user.getVerifiedAt())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must have email validated first!");
        }
        return true;
    }

    @Override
    public boolean upvotePost(long id, String upvotedByUsername) {
        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post doesn't exists!");
        }

        Post upvotedPost = postRepository.getReferenceById(id);
        User upvotedUser = userRepository.findByUsername(upvotedPost.getOwner());
        Set<String> usernamesWhoUpvoted = upvotedPost.getUpvotedByUsernames();
        Set<String> usernamesWhoDownvoted = upvotedPost.getDownvotedByUsernames();

        if (Objects.isNull(upvotedUser)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exists!");
        }

        if (checkEmailVerifiedAt(upvotedByUsername)) {
            if (usernamesWhoUpvoted.contains(upvotedByUsername)) {
                usernamesWhoUpvoted.remove(upvotedByUsername);
                upvotedPost.setReputation(upvotedPost.getReputation() - 1);
                upvotedUser.setKarma(upvotedUser.getKarma() - 1);
            } else if (usernamesWhoDownvoted.contains(upvotedByUsername)) {
                usernamesWhoDownvoted.remove(upvotedByUsername);
                usernamesWhoUpvoted.add(upvotedByUsername);
                upvotedPost.setReputation(upvotedPost.getReputation() + 2);
                upvotedUser.setKarma(upvotedUser.getKarma() + 2);
            } else {
                usernamesWhoUpvoted.add(upvotedByUsername);
                upvotedPost.setReputation(upvotedPost.getReputation() + 1);
                upvotedUser.setKarma(upvotedUser.getKarma() + 1);
            }
            postRepository.save(upvotedPost);
            userRepository.save(upvotedUser);
        }
        return true;
    }

    @Override
    public boolean downvotePost(long id, String downvotedByUsername) {
        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post doesn't exists!");
        }

        Post downvotedPost = postRepository.getReferenceById(id);
        User downvotedUser = userRepository.findByUsername(downvotedPost.getOwner());
        Set<String> usernamesWhoDownvoted = downvotedPost.getDownvotedByUsernames();
        Set<String> usernamesWhoUpvoted = downvotedPost.getUpvotedByUsernames();

        if (Objects.isNull(downvotedUser)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exists!");
        }

        if (checkEmailVerifiedAt(downvotedByUsername)) {
            if (usernamesWhoDownvoted.contains(downvotedByUsername)) {
                usernamesWhoDownvoted.remove(downvotedByUsername);
                downvotedPost.setReputation(downvotedPost.getReputation() + 1);
                downvotedUser.setKarma(downvotedUser.getKarma() + 1);
            } else if (usernamesWhoUpvoted.contains(downvotedByUsername)) {
                usernamesWhoUpvoted.remove(downvotedByUsername);
                usernamesWhoDownvoted.add(downvotedByUsername);
                downvotedPost.setReputation(downvotedPost.getReputation() - 2);
                downvotedUser.setKarma(downvotedUser.getKarma() - 2);
            } else {
                usernamesWhoDownvoted.add(downvotedByUsername);
                downvotedPost.setReputation(downvotedPost.getReputation() - 1);
                downvotedUser.setKarma(downvotedUser.getKarma() - 1);
            }
            postRepository.save(downvotedPost);
            userRepository.save(downvotedUser);
        }
        return true;
    }

    @Override
    public boolean deletePost(long id, String username) {
        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post doesn't exists!");
        }

        Post deletedPost = postRepository.getReferenceById(id);
        User user = userRepository.findByUsername(username);

        if (!deletedPost.getOwner().equals(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User isn't owner of the Post!");
        }

        postRepository.delete(deletedPost);
        return true;
    }

    @Override
    public List<Post> mapProfilePicturesAndReactions(String username) throws IOException {
        List<Post> storedPosts = postRepository.findAll();

        for (Post post : storedPosts) {
            User owner = userRepository.findByUsername(post.getOwner());


            if (post.getUpvotedByUsernames().contains(username)) {
                post.setReaction(1);
            } else if (post.getDownvotedByUsernames().contains(username)) {
                post.setReaction(-1);
            } else {
                post.setReaction(0);
            }

            byte[] fileContent = FileUtils.readFileToByteArray(new File(owner.getProfilePictureFilePath()));
            String encodedProfilePicture = Base64.getEncoder().encodeToString(fileContent);
            post.setOwnerProfilePicture(encodedProfilePicture);
            Set<Comment> commentsList = post.getComments();

            for (Comment comment : commentsList) {
                User commentOwner = userRepository.findByUsername(comment.getOwner());

                if (comment.getUpvotedByUsernames().contains(username)) {
                    comment.setReaction(1);
                } else if (comment.getDownvotedByUsernames().contains(username)) {
                    comment.setReaction(-1);
                } else {
                    comment.setReaction(0);
                }

                byte[] fileContentInComment = FileUtils.readFileToByteArray(new File(commentOwner.getProfilePictureFilePath()));
                String encodedProfilePictureInComment = Base64.getEncoder().encodeToString(fileContentInComment);
                comment.setOwnerProfilePicture(encodedProfilePictureInComment);
            }
        }
        return storedPosts;
    }
}
