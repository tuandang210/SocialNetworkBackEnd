package com.codegym.repository;

import com.codegym.model.like.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeCommentRepository extends JpaRepository<LikeComment,Long> {
    Iterable<LikeComment> findAllByCommentId(Long id);
}
