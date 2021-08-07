package com.codegym.repository;

import com.codegym.model.image.ImageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStatusImageRepository extends JpaRepository<ImageStatus, Long> {
    Optional<ImageStatus> findByUrl(String url);
}
