package com.codegym.service.status;

import com.codegym.model.account.Account;
import com.codegym.model.enumeration.EFriendStatus;
import com.codegym.model.status.Status;
import com.codegym.repository.IStatusRepository;
import com.codegym.service.accountRelation.IAccountRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatusService implements IStatusService{
    @Autowired
    private IStatusRepository statusRepository;

    @Autowired
    private IAccountRelationService accountRelationService;

    @Override
    public Iterable<Status> findAll() {
        return statusRepository.findAll();
    }

    @Override
    public Optional<Status> findById(Long id) {
        return statusRepository.findById(id);
    }

    @Override
    public Status save(Status status) {
        if (status.getPostedTime() == null) {
            status.setPostedTime(new Date());
        }
        return statusRepository.save(status);
    }

    @Override
    public void delete(Long id) {
        statusRepository.deleteById(id);
    }


    @Override
    public Iterable<Status> findAllPublicStatus() {
        return statusRepository.findAllByPrivacy_NameOrderByPostedTimeDesc("public");
    }

    @Override
    public Iterable<Status> findAllFriendStatus(Long id) {
        List<Account> friends = (List<Account>) accountRelationService.findAllByAccountIdAndStatus(id, EFriendStatus.FRIEND);
        Iterable<Status> friendModeStatuses = statusRepository.findAllByPrivacy_NameOrderByPostedTimeDesc("friend-only");

        List<Status> friendStatuses = new ArrayList<>();

        for (Status s: friendModeStatuses) {
            if (friends.contains(s.getAccount())) {
                friendStatuses.add(s);
            }
        }
        Collections.sort(friendStatuses, (status1, status2) -> (-1)*Long.valueOf(status1.getPostedTime().getTime()).compareTo(status2.getPostedTime().getTime()));

        return friendStatuses;
    }

    public Iterable<Status> findALlStatusInNewsFeed(Long id) {
        List<Status> newsFeed = new ArrayList<>();

        List<Status> publicStatus = (List<Status>) findAllPublicStatus();
        List<Status> friendStatus = (List<Status>) findAllFriendStatus(id);
        newsFeed.addAll(publicStatus);
        newsFeed.addAll(friendStatus);

        Collections.sort(newsFeed, (status1, status2) -> (-1)*Long.valueOf(status1.getPostedTime().getTime()).compareTo(status2.getPostedTime().getTime()));
        return newsFeed;
    }

    @Override
    public Iterable<Status> findAllByAccountId(Long id) {
        return null;
    }
}
