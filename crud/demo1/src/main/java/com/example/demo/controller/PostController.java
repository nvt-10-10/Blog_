package com.example.demo.controller;
import com.example.demo.model.entity.Post;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:8080")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }
    @GetMapping("/page/{page}")
    @JsonView(Post.PostFull.class)
    public ResponseEntity<List<Post>> getAllPosts(@PathVariable String page) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page) * 10, 10);
        List<Post> posts = postService.findAll(pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    @JsonView(Post.PostFull.class)
    public ResponseEntity<Map<String, Object>> getPostById(@PathVariable int postId) {
        Map<String, Object> post = postService.findPostByID(postId);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/user/{user_id}/post/{post_id}")
    @JsonView(Post.PostNotContent.class)
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable String user_id, @PathVariable String post_id) {
        List<Post> posts = postService.findAllByUserID(Integer.parseInt(user_id), post_id == null ? -1 : Integer.parseInt(post_id));
        if (!posts.isEmpty()) {
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/create")
    public ResponseEntity createPost(
            @RequestBody Map<String, String> params
    ) {
        postService.create(params);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity updatePost(@PathVariable int postId, @RequestBody Post post) {
        Optional<Post> existingPost = postService.findById(postId);
        if (existingPost.isPresent()) {
            post.setId(postId);
            Post updatedPost = postService.save(post);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable int postId) {
        postService.deleteById(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
