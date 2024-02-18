package com.example.demo.repository;

import com.example.demo.model.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment,Integer> {

    @Query("SELECT c as comment , GROUP_CONCAT(l.user.id)  as userLike " +
            "FROM Comment c LEFT JOIN Like l on l.type_id = c.id  and l.type='comment' " +
            "WHERE c.post.id = :post_id and c.path = cast(c.id as string ) " +
            "group by c.id order by  c.date desc")
    List<Object[]> findAllRootComments(@Param("post_id") int post_id, Pageable pageable);
    @Query("SELECT c as comment ,GROUP_CONCAT(l.user.id) as userLike " +
            "FROM Comment c LEFT JOIN Like l on l.type_id = c.id and l.type='comment'  " +
            "WHERE c.post.id = :post_id AND c.path = concat(:path,'_',c.id) " +
            "group by c.id order by  c.date desc")
    List<Object[]> findAllCommentPath(@Param("post_id") int post_id,@Param("path") String path, Pageable pageable);
    @Modifying
    @Query("update  Comment  c set c.count_comment = c.count_comment+1 where  c.id=:comment_id")
    void incrementCommentCount(@Param("comment_id") int id);
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.path LIKE :path%")
    int  deleteByPath(@Param("path") String path);
    @Query("SELECT count(c) FROM Comment  c " +
            "WHERE c.post.id = :post_id and c.path = cast(c.id as string ) ")
    int getLengthRoot(@Param("post_id") int post_id);
}
