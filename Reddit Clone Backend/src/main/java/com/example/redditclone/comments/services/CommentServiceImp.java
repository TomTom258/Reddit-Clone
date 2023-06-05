package com.example.redditclone.comments.services;

import com.example.redditclone.comments.models.Comment;
import com.example.redditclone.comments.repositories.CommentRepository;
import com.example.redditclone.posts.models.Post;
import com.example.redditclone.posts.repositories.PostRepository;
import com.example.redditclone.users.models.Role;
import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.RoleRepository;
import com.example.redditclone.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Set;

@Service
public class CommentServiceImp implements CommentService {

    CommentRepository commentRepository;
    UserRepository userRepository;
    PostRepository postRepository;
    RoleRepository roleRepository;

    @Autowired
    public CommentServiceImp(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository,
                             RoleRepository roleRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.roleRepository = roleRepository;
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
    public boolean upvoteComment(long id, String upvotedByUsername) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment doesn't exists!");
        }

        Comment upvotedComment = commentRepository.getReferenceById(id);
        User upvotedUser = userRepository.findByUsername(upvotedComment.getOwner());
        Set<String> usernamesWhoUpvoted = upvotedComment.getUpvotedByUsernames();
        Set<String> usernamesWhoDownvoted = upvotedComment.getDownvotedByUsernames();

        if (Objects.isNull(upvotedUser)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exists!");
        }

        if (checkEmailVerifiedAt(upvotedByUsername)) {
            if (usernamesWhoUpvoted.contains(upvotedByUsername)) {
                usernamesWhoUpvoted.remove(upvotedByUsername);
                upvotedComment.setReputation(upvotedComment.getReputation() - 1);
                upvotedUser.setKarma(upvotedUser.getKarma() - 1);
            } else if (usernamesWhoDownvoted.contains(upvotedByUsername)) {
                usernamesWhoDownvoted.remove(upvotedByUsername);
                usernamesWhoUpvoted.add(upvotedByUsername);
                upvotedComment.setReputation(upvotedComment.getReputation() + 2);
                upvotedUser.setKarma(upvotedUser.getKarma() + 2);
            } else {
                usernamesWhoUpvoted.add(upvotedByUsername);
                upvotedComment.setReputation(upvotedComment.getReputation() + 1);
                upvotedUser.setKarma(upvotedUser.getKarma() + 1);
            }
            commentRepository.save(upvotedComment);
            userRepository.save(upvotedUser);
        }
        return true;
    }

    @Override
    public boolean downvoteComment(long id, String downvotedByUsername) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment doesn't exists!");
        }

        Comment downvotedComment = commentRepository.getReferenceById(id);
        User downvotedUser = userRepository.findByUsername(downvotedComment.getOwner());
        Set<String> usernamesWhoUpvoted = downvotedComment.getUpvotedByUsernames();
        Set<String> usernamesWhoDownvoted = downvotedComment.getDownvotedByUsernames();

        if (Objects.isNull(downvotedUser)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exists!");
        }

        if (checkEmailVerifiedAt(downvotedByUsername)) {
            if (usernamesWhoDownvoted.contains(downvotedByUsername)) {
                usernamesWhoDownvoted.remove(downvotedByUsername);
                downvotedComment.setReputation(downvotedComment.getReputation() + 1);
                downvotedUser.setKarma(downvotedUser.getKarma() + 1);
            } else if (usernamesWhoUpvoted.contains(downvotedByUsername)) {
                usernamesWhoUpvoted.remove(downvotedByUsername);
                usernamesWhoDownvoted.add(downvotedByUsername);
                downvotedComment.setReputation(downvotedComment.getReputation() - 2);
                downvotedUser.setKarma(downvotedUser.getKarma() - 2);
            } else {
                usernamesWhoDownvoted.add(downvotedByUsername);
                downvotedComment.setReputation(downvotedComment.getReputation() - 1);
                downvotedUser.setKarma(downvotedUser.getKarma() - 1);
            }
            commentRepository.save(downvotedComment);
            userRepository.save(downvotedUser);
        }
        return true;
    }


    @Override
    public boolean deleteComment(long id, String username) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post doesn't exists!");
        }

        Comment deletedComment = commentRepository.getReferenceById(id);
        User user = userRepository.findByUsername(username);
        User owner = userRepository.findByUsername(deletedComment.getOwner());
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");

        boolean isUserAlsoOwner = owner.equals(user);
        boolean hasUserRole = user.getRoles().contains(adminRole);

        if (!isUserAlsoOwner && !hasUserRole) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User can't delete this Comment!");
        }

        Long postId = commentRepository.getReferenceById(id).getPostId();
        Post editedPost = postRepository.getReferenceById(postId);
        Set<Comment> commentsList = editedPost.getComments();

        commentsList.remove(deletedComment);
        commentRepository.delete(deletedComment);
        editedPost.setComments(commentsList);
        postRepository.save(editedPost);
        return true;
    }
}
