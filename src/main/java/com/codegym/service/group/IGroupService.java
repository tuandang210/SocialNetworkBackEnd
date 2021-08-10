package com.codegym.service.group;

import com.codegym.model.account.Account;
import com.codegym.model.group.Group;
import com.codegym.service.IGeneralService;

public interface IGroupService extends IGeneralService<Group> {
    Iterable<Group> findGroupsByAccount(Account account);

    Iterable<Group> findGroupsByNameContaining(String name, Account account);
}
