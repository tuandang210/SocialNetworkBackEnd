package com.codegym.service.accountRelation;

import com.codegym.model.account.Account;
import com.codegym.model.enumeration.EFriendStatus;
import com.codegym.model.friend.AccountRelation;
import com.codegym.repository.IAccountRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AccountRelationService implements IAccountRelationService{
    @Autowired
    private IAccountRelationRepository accountRelationRepository;

    @Override
    public Iterable<AccountRelation> findAll() {
        return accountRelationRepository.findAll();
    }

    @Override
    public Optional<AccountRelation> findById(Long id) {
        return accountRelationRepository.findById(id);
    }

    @Override
    public AccountRelation save(AccountRelation accountRelation) {
        return accountRelationRepository.save(accountRelation);
    }

    @Override
    public void delete(Long id) {
        accountRelationRepository.deleteById(id);
    }

    @Override
    public Optional<AccountRelation> findByTwoAccountIds(Long id1, Long id2) {
        Optional<AccountRelation> accountRelation1 = accountRelationRepository.findByAccount1_IdAndAccount2_Id(id1, id2);
        Optional<AccountRelation> accountRelation2 = accountRelationRepository.findByAccount1_IdAndAccount2_Id(id2, id1);
        if (accountRelation1.isPresent()) {
            return accountRelation1;
        } else {
            return accountRelation2;
        }
    }

    @Override
    public Iterable<AccountRelation> findAllByAccountId(Long id) {
        return accountRelationRepository.findAllByAccount1_IdOrAccount2_Id(id, id);
    }

    @Override
    public Iterable<Account> findAllByAccountIdAndStatus(Long id, EFriendStatus status) {
        Iterable<AccountRelation> relations = findAllByAccountId(id);
        List<Account> statusAccounts = new ArrayList<>();
        for (AccountRelation relation: relations) {
            if (relation.getFriendStatus().getStatus().equals(status)) {
                if (relation.getAccount1().getId().equals(id)){
                    statusAccounts.add(relation.getAccount2());
                } else {
                    statusAccounts.add(relation.getAccount1());
                }
            }
        }
        return statusAccounts;
    }

    @Override
    public Iterable<Account> findAllFriendRequestSender(Long id) {
        Iterable<AccountRelation> pending = accountRelationRepository.findAllByAccount2_IdAndFriendStatus_Status(id, EFriendStatus.PENDING);
        List<Account> senders = new ArrayList<>();

        for (AccountRelation relation: pending) {
            senders.add(relation.getAccount1());
        }
        return senders;
    }

    @Override
    public Iterable<Account> findAllFriendRequestReceiver(Long id) {
        Iterable<AccountRelation> pending = accountRelationRepository.findAllByAccount1_IdAndFriendStatus_Status(id, EFriendStatus.PENDING);
        List<Account> receivers = new ArrayList<>();

        for (AccountRelation relation: pending) {
            receivers.add(relation.getAccount2());
        }
        return receivers;
    }

    @Override
    public Iterable<Account> findMutualFriends(Long id1, Long id2) {
        Iterable<Account> friendList1 = findAllByAccountIdAndStatus(id1, EFriendStatus.FRIEND);
        Iterable<Account> friendList2 = findAllByAccountIdAndStatus(id2, EFriendStatus.FRIEND);
        List<Account> mutualFriends = new ArrayList<>();

        for (Account friend1: friendList1) {
            for (Account friend2: friendList2) {
                if (friend1.equals(friend2)) {
                    mutualFriends.add(friend1);
                }
            }
        }
        return mutualFriends;
    }
}
