package com.codegym.repository;

import com.codegym.model.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusRepository extends JpaRepository<Status,Long> {

    Iterable<Status> findAllByPrivacy_NameOrderByPostedTimeDesc(String privacy);

    Iterable<Status> findAllByAccount_IdAndPrivacy_NameOrderByPostedTimeDesc(Long id, String privacy);
}
