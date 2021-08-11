package com.codegym.repository;

import com.codegym.model.page.MemberPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMemberPageRepository extends JpaRepository<MemberPage, Long> {
    Optional<MemberPage> findMemberPageByAccount_Id(Long id);
    Iterable<MemberPage> findAllByPageId(Long pageId);
    Iterable<MemberPage> findAllByAccountId(Long accountId);
}
