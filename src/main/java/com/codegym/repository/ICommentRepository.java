package com.codegym.repository;

import com.codegym.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment,Long> {
    Iterable<Comment> findAllByStatusId(Long id);
}
