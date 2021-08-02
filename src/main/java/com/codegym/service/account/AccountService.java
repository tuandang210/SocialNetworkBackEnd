package com.codegym.service.account;

import com.codegym.model.account.Account;
import com.codegym.model.dto.AccountPrincipal;
import com.codegym.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if (account.getAddress() == null) {
            account.setAddress("");
        }
        if (account.getAvatar() == null) {
            account.setAvatar("https://firebasestorage.googleapis.com/v0/b/social-network-d0202.appspot.com/o/sbcf-default-avatar.png?alt=media&token=900d125c-9b2d-47b1-9c86-bd646a1f53b8");
        }
        if (account.getFavorite() == null) {
            account.setFavorite("");
        }
        if (account.getFullName() == null) {
            account.setFullName("");
        }
        return accountRepository.save(account);
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        return AccountPrincipal.build(account);
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account saveVer(Account account) {
        if (account.getAvatar() == null) {
            account.setAvatar("https://firebasestorage.googleapis.com/v0/b/social-network-d0202.appspot.com/o/sbcf-default-avatar.png?alt=media&token=900d125c-9b2d-47b1-9c86-bd646a1f53b8");
        }
        return accountRepository.save(account);
    }

    @Override
    public List<Account> PaginationAccount(int number) {
        return accountRepository.PaginationAccount(number);
    }

    @Override
    public boolean existsAccountByUsername(String username) {
        return accountRepository.existsAccountByUsername(username);
    }

    @Override
    public boolean existsAccountByEmail(String email) {
        return accountRepository.existsAccountByEmail(email);
    }
}
