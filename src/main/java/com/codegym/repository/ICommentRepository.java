package com.codegym.repository;

import com.codegym.model.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment,Long> {
    Iterable<Comment> findAllByStatusId(Long id);

    Iterable<Comment> findAllByStatus_IdOrderByDateDesc(Long id);

    Page<Comment> findAllByStatus_IdOrderByDateDesc(Long id, Pageable pageable);

    @Query(value = "select * from comment join status s on s.id = comment.status_id where status_id=?1;", nativeQuery = true)
    Iterable<Comment> getAllCommentByStatusId( );
}
