package com.codegym.repository;

import com.codegym.model.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusRepository extends JpaRepository<Status,Long> {
    Iterable<Status> findAllByAccountId(Long id);
}
