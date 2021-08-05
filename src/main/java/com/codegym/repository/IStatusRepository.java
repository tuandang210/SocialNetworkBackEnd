package com.codegym.repository;

import com.codegym.model.status.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusRepository extends JpaRepository<Status, Long> {
    Iterable<Status> findAllByPrivacy_NameOrderByPostedTimeDesc(String privacy);

    Iterable<Status> findAllByPrivacy_NameAndAccount_IdOrderByPostedTimeDesc(String privacy, Long id);

    Iterable<Status> findAllByAccountIdOrderByPostedTimeDesc(Long id);

    Page<Status> findAllByAccountIdOrderByPostedTimeDesc(Long id, Pageable pageable);
}
