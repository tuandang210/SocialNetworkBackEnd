package com.codegym.repository;

import com.codegym.model.page.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPageRepository extends JpaRepository<Page,Long> {
    Optional<Page> findPageByName(String name);
}
