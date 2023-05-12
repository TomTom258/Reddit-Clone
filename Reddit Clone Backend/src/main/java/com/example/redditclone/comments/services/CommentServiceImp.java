package com.example.redditclone.comments.services;

import com.example.redditclone.comments.models.Comment;
import com.example.redditclone.comments.repositories.CommentRepository;
import com.example.redditclone.posts.models.Post;
import com.example.redditclone.posts.repositories.PostRepository;
import com.example.redditclone.users.models.User;
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

    @Autowired
    public CommentServiceImp(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }
    @Override
    public boolean upvoteComment(long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment doesn't exists!");
        }

        Comment upvotedComment = commentRepository.getReferenceById(id);
        User upvotedUser = userRepository.findByUsername(upvotedComment.getOwner());

        if (Objects.isNull(upvotedUser)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exists!");
        } else {
            upvotedComment.setReputation(upvotedComment.getReputation() + 1);
            upvotedUser.setKarma(upvotedUser.getKarma() + 1);
            commentRepository.save(upvotedComment);
            userRepository.save(upvotedUser);
            return true;
        }
    }

    @Override
    public boolean downvoteComment(long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment doesn't exists!");
        }

        Comment downvotedComment = commentRepository.getReferenceById(id);
        User downvotedUser = userRepository.findByUsername(downvotedComment.getOwner());

        if (Objects.isNull(downvotedUser)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exists!");
        } else {
            downvotedComment.setReputation(downvotedComment.getReputation() - 1);
            downvotedUser.setKarma(downvotedUser.getKarma() - 1);
            commentRepository.save(downvotedComment);
            userRepository.save(downvotedUser);
            return true;
        }
    }

    @Override
    public boolean deleteComment(long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post doesn't exists!");
        }

        Comment deletedComment = commentRepository.getReferenceById(id);
        User user = userRepository.findByUsername(deletedComment.getOwner());

        if (Objects.isNull(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exists!");
        } else {
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
}
