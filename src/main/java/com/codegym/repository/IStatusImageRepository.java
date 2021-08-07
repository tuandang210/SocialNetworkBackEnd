package com.codegym.repository;

import com.codegym.model.image.ImageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusImageRepository extends JpaRepository<ImageStatus, Long> {
    Iterable<ImageStatus> findAllByStatus_Id(Long id);
}
