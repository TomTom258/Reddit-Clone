package com.example.redditclone.comments.services;

import com.example.redditclone.comments.models.Comment;
import com.example.redditclone.comments.repositories.CommentRepository;
import com.example.redditclone.dtos.CommentDto;
import com.example.redditclone.posts.models.Post;
import com.example.redditclone.posts.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Service
public class CommentValidatorImp implements CommentValidator{

    PostRepository postRepository;
    CommentRepository commentRepository;

    @Autowired
    public CommentValidatorImp(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }
    @Override
    public boolean validateId(Long id) {
        Optional<Post> post = postRepository.findById(id);

        if (!post.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post wasn't found!");
        }
        return true;
    }

    @Override
    public boolean validateContent(String content) {
        if (content.length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comment must be at least 2 characters long!");
        }
        return true;
    }

    @Override
    public boolean addComment(CommentDto commentDto, long id) {
        boolean isIdValid = validateId(id);
        boolean isContentValid = validateContent(commentDto.getContent());

        if (isIdValid && isContentValid) {
            Post commentedPost = postRepository.getReferenceById(id);
            Comment newComment = new Comment(commentDto.getContent(), "testOwner");
            Set<Comment> commentsList = commentedPost.getComments();

            commentsList.add(newComment);
            commentedPost.setComments(commentsList);
            postRepository.save(commentedPost);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown error");
        }
    }

    @Override
    public boolean editTheComment(CommentDto commentDto, Long commentId) {
        Long postId = commentRepository.getReferenceById(commentId).getPostId();

        boolean isIdValid = validateId(postId);
        boolean isContentValid = validateContent(commentDto.getContent());

        if (isIdValid && isContentValid) {
            Post editedPost = postRepository.getReferenceById(postId);
            Comment editedComment = commentRepository.getReferenceById(commentId);
            Set<Comment> commentsList = editedPost.getComments();

            editedComment.setContent(commentDto.getContent());
            commentsList.add(editedComment);
            editedPost.setComments(commentsList);
            postRepository.save(editedPost);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown error");
        }
    }
}
