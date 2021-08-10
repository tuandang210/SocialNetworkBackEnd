package com.codegym.repository;

import com.codegym.model.account.Account;
import com.codegym.model.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupRepository extends JpaRepository<Group,Long> {
    Iterable<Group> findGroupsByAccount(Account account);

    Iterable<Group> findGroupsByNameContainingAndAccount(String name,Account account);
}
