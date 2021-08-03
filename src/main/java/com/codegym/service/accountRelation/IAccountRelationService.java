package com.codegym.service.accountRelation;

import com.codegym.model.account.Account;
import com.codegym.model.enumeration.EFriendStatus;
import com.codegym.model.friend.AccountRelation;
import com.codegym.service.IGeneralService;

import java.util.Optional;

public interface IAccountRelationService extends IGeneralService<AccountRelation> {
    Optional<AccountRelation> findByTwoAccountIds(Long id1, Long id2);

    Iterable<AccountRelation> findAllByAccountId(Long id);

    Iterable<Account> findAllByAccountIdAndStatus(Long id, EFriendStatus status);

    Iterable<AccountRelation> findAllBySenderIdAndStatus(Long id1, EFriendStatus status);

    Iterable<AccountRelation> findAllByReceiverIdAndStatus(Long id2, EFriendStatus status);

    Iterable<Account> findMutualFriends(Long id1, Long id2);
}
