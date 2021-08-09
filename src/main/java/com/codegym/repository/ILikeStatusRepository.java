package com.codegym.repository;

import com.codegym.model.like.LikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILikeStatusRepository extends JpaRepository<LikeStatus,Long> {
    Iterable<LikeStatus> findAllByStatusId(Long id);
    Iterable<LikeStatus> findAllByStatusIdAndIsLikeTrue(Long id);
}
