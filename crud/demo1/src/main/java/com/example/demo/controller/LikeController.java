package com.example.demo.controller;
import com.example.demo.model.entity.Like;
import com.example.demo.service.LikeService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class LikeController {
    private final LikeService likeService;
    @JsonView(Like.likePublic.class)
    @PostMapping("")
    public ResponseEntity<Like> createLikePost(@RequestBody Map<String, String> params) {
        return new ResponseEntity<>(likeService.create(params), HttpStatus.CREATED);
    }
    @DeleteMapping("/type_id/{type_id}/type/{type}/user_id/{user_id}")
    public ResponseEntity<Void> delete(@PathVariable int type_id, @PathVariable String type,@PathVariable int user_id) {
        likeService.deleteByTypeIdAndType(type_id, type,user_id);
        return ResponseEntity.noContent().build();
    }
}
