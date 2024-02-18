package com.example.demo.controller;
import com.example.demo.model.entity.Comment;
import com.example.demo.service.CommentService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")

public class CommentController {
    private final CommentService commentService;
    @GetMapping
    @JsonView(Comment.comment.class)
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.findAll();
        return ResponseEntity.ok(comments);
    }
    @GetMapping("/{id}")
    @JsonView(Comment.comment.class)
    public ResponseEntity<List<Map<String, Object>>> getCommentById(@PathVariable int id) {
        Optional<Comment> optionalComment = commentService.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            Pageable pageable = PageRequest.of(0, 10);
            List<Map<String, Object>> comments = commentService.findAllCommentByPostID(comment.getPost().getId(), pageable);
            return ResponseEntity.ok(comments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/comment-reply/{id}")
    @JsonView(Comment.comment.class)
    public ResponseEntity<List<Map<String, Object>>> getCommentReplyById(@PathVariable int id) {
        Optional<Comment> optionalComment = commentService.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            Pageable pageable = PageRequest.of(0, 10);
            List<Map<String, Object>> comments = commentService.findAllCommentByCommentPath(comment.getPost().getId(), comment.getPath(), pageable);
            return ResponseEntity.ok(comments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/post/{id}")
    @JsonView(Comment.comment.class)
    public ResponseEntity<List<Map<String, Object>>> getCommentByPostID(@PathVariable int id) {
        Pageable pageable = PageRequest.of(0, 10);
        List<Map<String, Object>> comments = commentService.findAllCommentByPostID(id, pageable);
        return ResponseEntity.ok(comments);
    }
    @GetMapping("/post/{id}/page/{page}")
    @JsonView(Comment.comment.class)
    public ResponseEntity<Map<String, Object>> getCommentByPostID(@PathVariable int id, @PathVariable int page) {
        Pageable pageable = PageRequest.of(page * 10, 10);
        Map<String, Object> comments = commentService.findAllCommentByPostIDAndUser(id, pageable);
        return ResponseEntity.ok(comments);
    }
    @PostMapping("/create")
    @JsonView(Comment.comment.class)
    public ResponseEntity<Map<String, Object>> createComment(@RequestBody Map<String, String> params) {
        Map<String, Object> result = commentService.createComment(params);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @JsonView(Comment.comment.class)
    @PostMapping("/create-reply")
    public ResponseEntity<Map<String, Object>> createCommentReply(@RequestBody Map<String, String> params) {
        Map<String, Object> result = commentService.createCommentReply(params);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @PatchMapping("/update")
    @JsonView(Comment.comment.class)
    public ResponseEntity<Comment> updateComment(@RequestBody Map<String, String> params) {
        String commentId = params.get("comment_id");
        String content = params.get("content");

        Optional<Comment> optionalComment = commentService.findById(Integer.parseInt(commentId));
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(content);
            comment.setEdited(true);
            commentService.update(comment);
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    @JsonView(Comment.comment.class)
    public ResponseEntity<Void> deleteComment(@PathVariable int id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
