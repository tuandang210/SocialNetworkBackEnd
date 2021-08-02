package com.codegym.service.account;

import com.codegym.model.account.Account;
import com.codegym.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IAccountService extends IGeneralService<Account>, UserDetailsService {
    Account findByUsername(String username);

    Account saveVer(Account account);

    List<Account> PaginationAccount(int number);

}
