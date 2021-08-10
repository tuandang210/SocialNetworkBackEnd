package com.codegym.repository;

import com.codegym.model.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);

    Page<Account> findAccountsByUsernameContaining(String username, Pageable pageable);

    @Query(value = "select * from account join account_roles ar on account.id = ar.account_id group by account_id order by account_id desc limit 2 offset ?1", nativeQuery = true)
    List<Account> PaginationAccount(int number);

    boolean existsAccountByUsername(String username);

    boolean existsAccountByEmail(String email);

    boolean existsAccountById(Long id);
}
