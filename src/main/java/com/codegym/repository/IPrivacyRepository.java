package com.codegym.repository;

import com.codegym.model.account.Privacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPrivacyRepository extends JpaRepository<Privacy, Long> {
}
