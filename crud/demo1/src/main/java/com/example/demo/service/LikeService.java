package com.example.demo.service;

import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.Like;
import com.example.demo.model.entity.Post;
import com.example.demo.model.entity.User;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService implements GenericInterface<Like>{
    private final LikeRepository repo;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentService commentService;
    @Override
    public List<Like> findAll() {
        return (List<Like>) repo.findAll();
    }
    @Override
    public Optional<Like> findById(int id) {
        return  repo.findById(id);
    }
    @Transactional(rollbackOn = Exception.class)
    public Like save(Like like){
        return repo.save(like);
    }

    public Like create(Map<String,String> params){
        String user_id = params.get("user_id");
        String type_id = params.get("type_id");
        String type = params.get("type");

        User user = userRepository.findById(Integer.parseInt(user_id)).get();
        Like like = new Like();
        like.setUser(user);
        like.setType_id(Integer.parseInt(type_id));
        like.setType(type);
        like = save(like);

        if (type.equals("post")){
            Post post = postRepository.findById(Integer.parseInt(type_id)).get();
            post.setCount_like(post.getCount_like()+1);
            postRepository.save(post);
        } else {
            Comment comment = commentService.findById(Integer.parseInt(type_id)).get();
            comment.setCount_like(comment.getCount_like()+1);
            commentService.update(comment);
        }
        return  like;
    }
    public void saveAll(List<Like> likes){
        repo.saveAll(likes);
    }

    public void deleteByTypeIdAndType(int type_id,String type,int user_id){
        if (type.equals("comment")){
            Comment comment = commentService.findById(type_id).get();
            comment.setCount_like(comment.getCount_like()-1);
            commentService.update(comment);
        } else {
            Post post = postRepository.findById(type_id).get();
            post.setCount_like(post.getCount_like()-1);
            postRepository.save(post);
        }

        repo.deleteByTypeIdAndType(type_id,type,user_id);
    }
    @Override
    public void deleteById(int id) {
        repo.deleteById(id);
    }
}
