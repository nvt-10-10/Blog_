package com.example.demo.service;

import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.User;
import com.example.demo.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService implements GenericInterface<Comment> {

    private final CommentRepository repo;
    private final UserService userService;
    private final PostService postService;

    @Override
    public List<Comment> findAll() {
        return (List<Comment>) repo.findAll();
    }

    @Override
    public Optional<Comment> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getPath() != null && comment.getPath().length() > 0) {
            String[] pathSegments = comment.getPath().split("_");
            for (String pathSegment : pathSegments) {
                int commentId = Integer.parseInt(pathSegment);
                repo.incrementCommentCount(commentId);
            }
        }
        return repo.save(comment);
    }

    public Comment update(Comment comment) {
        return repo.save(comment);
    }

    @Override
    public void saveAll(List<Comment> comments) {
        repo.saveAll(comments);
    }

    @Override
    public void deleteById(int id) {
        Comment comment = repo.findById(id).get();
        Post post = comment.getPost();
        int row = repo.deleteByPath(comment.getPath());
        if (comment.getParent()!=null){
            Comment commentParent = repo.findById(Integer.parseInt(comment.getParent())).get();
            String paths[] = commentParent.getPath().split("_");
            for(String path : paths){
                commentParent = repo.findById(Integer.parseInt(path)).get();
                commentParent.setCount_comment(commentParent.getCount_comment()-row);
                update(comment);
            }

        }
        post.setCount_comment(post.getCount_comment() - row);
    }

    public Map<String, Object> createCommentReply(Map<String, String> params) {
        String user_id = params.get("user_id");
        String comment_id = params.get("comment_id");
        String content = params.get("content");
        User user = userService.findById(Integer.parseInt(user_id)).isPresent()? userService.findById(Integer.parseInt(user_id)).get() : null;
        Comment comment = findById(Integer.parseInt(comment_id)).isPresent() ? findById(Integer.parseInt(comment_id)).get() : null;
        assert comment != null;
        Post post = comment.getPost();
        Comment commentReply = new Comment();
        commentReply.setPost(post);
        commentReply.setUser(user);
        commentReply.setContent(content);
        commentReply.setPath(comment.getPath());
        commentReply.setParent(comment_id.toString());
        commentReply = save(commentReply);
        commentReply.setPath(comment.getPath() + "_" + commentReply.getId());
        commentReply=update(commentReply);
        Map<String, Object> result = new HashMap<>();
        result.put("user_id_like", -1);
        result.put("comment", commentReply);
        comment.setCount_comment(comment.getCount_comment()+1);
        update(comment);
        post.setCount_comment((post.getCount_comment() + 1));
        postService.save(post);


        return result;
    }

    public Map<String, Object> createComment(Map<String, String> params) {
        String user_id = params.get("user_id");
        String post_id = params.get("post_id");
        String content = params.get("content");

        Post post =  postService.findById(Integer.parseInt(post_id)).isPresent() ? postService.findById(Integer.parseInt(post_id)).get() : null;
        User user = userService.findById(Integer.parseInt(user_id)).isPresent() ? userService.findById(Integer.parseInt(user_id)).get() : null;

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);
        comment = save(comment);
        comment.setPath(String.valueOf(comment.getId()));

        update(comment);
        assert post != null;
        post.setCount_comment((post.getCount_comment() + 1));
        postService.save(post);
        Map<String, Object> result = new HashMap<>();
        result.put("user_id_like", -1);
        result.put("comment", comment);
        return result;

    }

    public List<Map<String, Object>> findAllCommentByPostID(int post_id, Pageable pageable) {
        List<Map<String, Object>> results = new ArrayList<>();
        List<Object[]> comments = repo.findAllRootComments(post_id, pageable);
        for (Object[] comment : comments
        ) {
            Map<String, Object> map = new HashMap<>();
            if (comment[1] != null) {
                map.put("user_id_like", comment[1]);
            } else {
                map.put("user_id_like", -1);
            }
            map.put("comment", comment[0]);
            results.add(map);
        }
        return results;
    }

    public Map<String, Object> findAllCommentByPostIDAndUser(int post_id, Pageable pageable) {
        Map<String, Object> results = new HashMap<>();
        List<Object[]> comments = repo.findAllRootComments(post_id, pageable);
        List<Map<String,Object>> commentData = new ArrayList<>();
        for (Object[] comment : comments
        ) {
            Map<String, Object> map = new HashMap<>();
            if (comment[1] != null) {
                map.put("user_id_like", comment[1]);
            } else {
                map.put("user_id_like", -1);
            }
            map.put("comment", comment[0]);
            commentData.add(map);
        }

        results.put("length",getLengthCommentRoot(post_id));
        results.put("comments",commentData);

        return results;
    }

    public List<Map<String, Object>> findAllCommentByCommentPath(int post_id, String path, Pageable pageable) {
        List<Map<String, Object>> results = new ArrayList<>();
        List<Object[]> comments = repo.findAllCommentPath(post_id, path, pageable);
        for (Object[] comment : comments
        ) {
            Map<String, Object> map = new HashMap<>();
            if (comment[1] != null) {
                map.put("user_id_like", comment[1]);
            } else {
                map.put("user_id_like", -1);
            }
            map.put("comment", comment[0]);

            results.add(map);
        }
        return results;
    }

    public int getLengthCommentRoot(int post_id){
        return repo.getLengthRoot(post_id);
    }



}
