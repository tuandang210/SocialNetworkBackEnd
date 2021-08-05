package com.codegym.service.status;

import com.codegym.model.account.Account;
import com.codegym.model.enumeration.EFriendStatus;
import com.codegym.model.status.Status;
import com.codegym.repository.IStatusRepository;
import com.codegym.service.accountRelation.IAccountRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatusService implements IStatusService {
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
    public Iterable<Status> findAllPublicStatusByOther(Long id) {
        Iterable<Status> allPublicStatus = statusRepository.findAllByPrivacy_NameOrderByPostedTimeDesc("public");
        List<Status> guestPublicStatus = new ArrayList<>();
        for (Status s : allPublicStatus) {
            if (s.getAccount().getId() != id) {
                guestPublicStatus.add(s);
            }
        }
        getHours(guestPublicStatus);
        return guestPublicStatus;
    }

    @Override
    public Iterable<Status> findAllPublicStatusByMyself(Long id) {
        Iterable<Status> allPublicStatus = statusRepository.findAllByPrivacy_NameOrderByPostedTimeDesc("public");
        List<Status> ownPublicStatus = new ArrayList<>();
        for (Status s : allPublicStatus) {
            if (s.getAccount().getId() == id) {
                ownPublicStatus.add(s);
            }
        }
        getHours(ownPublicStatus);
        return ownPublicStatus;
    }

    @Override
    public Iterable<Status> findAllFriendStatus(Long id) {
        List<Account> friends = (List<Account>) accountRelationService.findAllByAccountIdAndStatus(id, EFriendStatus.FRIEND);
        Iterable<Status> friendModeStatuses = statusRepository.findAllByPrivacy_NameOrderByPostedTimeDesc("friend-only");

        List<Status> friendStatuses = new ArrayList<>();

        for (Status s : friendModeStatuses) {
            if (friends.contains(s.getAccount())) {
                friendStatuses.add(s);
            }
        }
        Collections.sort(friendStatuses, (status1, status2) -> (-1) * Long.valueOf(status1.getPostedTime().getTime()).compareTo(status2.getPostedTime().getTime()));
        getHours(friendStatuses);
        return friendStatuses;
    }

    @Override
    public Iterable<Status> findAllNonPrivateStatusByMyself(Long id) {
        List<Status> nonPrivateStatus = new ArrayList<>();

        List<Status> publics = (List<Status>) statusRepository.findAllByPrivacy_NameAndAccount_IdOrderByPostedTimeDesc("public", id);
        List<Status> friend_only = (List<Status>) statusRepository.findAllByPrivacy_NameAndAccount_IdOrderByPostedTimeDesc("friend-only", id);

        nonPrivateStatus.addAll(publics);
        nonPrivateStatus.addAll(friend_only);

        Collections.sort(nonPrivateStatus, (status1, status2) -> (-1) * Long.valueOf(status1.getPostedTime().getTime()).compareTo(status2.getPostedTime().getTime()));
        getHours(nonPrivateStatus);
        return nonPrivateStatus;
    }

    public Iterable<Status> findAllStatusInNewsFeed(Long id) {
        List<Status> newsFeed = new ArrayList<>();

        List<Status> publicStatus = (List<Status>) findAllPublicStatusByOther(id);
        List<Status> friendStatus = (List<Status>) findAllFriendStatus(id);
        List<Status> nonPrivateOwnStatus = (List<Status>) findAllNonPrivateStatusByMyself(id);
        newsFeed.addAll(publicStatus);
        newsFeed.addAll(friendStatus);
        newsFeed.addAll(nonPrivateOwnStatus);

        Collections.sort(newsFeed, (status1, status2) -> (-1) * Long.valueOf(status1.getPostedTime().getTime()).compareTo(status2.getPostedTime().getTime()));
        getHours(newsFeed);
        return newsFeed;
    }

    @Override
    public Iterable<Status> findAllByAccountId(Long id) {
        return statusRepository.findAllByAccountIdOrderByPostedTimeDesc(id);
    }

    @Override
    public Iterable<Status> findAllStatusInNewsFeedPagination(Long id, Long pageSize) {
        List<Status> newsfeed = (List<Status>) findAllStatusInNewsFeed(id);
        List<Status> shortcut = new ArrayList<>();
        if (newsfeed.size() < pageSize) {
            return newsfeed;
        }
        for (int i = 0; i < pageSize; i++) {
            shortcut.add(newsfeed.get(i));
        }
        getHours(shortcut);
        return shortcut;
    }

    @Override
    public Iterable<Status> findAllPublicStatusByMyselfPagination(Long id, Long pageSize) {
        List<Status> publics = (List<Status>) findAllPublicStatusByMyself(id);
        List<Status> shortcut = new ArrayList<>();
        if (publics.size() < pageSize) {
            return publics;
        }
        for (int i = 0; i < pageSize; i++) {
            shortcut.add(publics.get(i));
        }
        getHours(shortcut);
        return shortcut;
    }

    @Override
    public Iterable<Status> findAllNonPrivateStatusByMySelfPagination(Long id, Long pageSize) {
        List<Status> nonPrivate = (List<Status>) findAllNonPrivateStatusByMyself(id);
        List<Status> shortcut = new ArrayList<>();
        if (nonPrivate.size() < pageSize) {
            return nonPrivate;
        }
        for (int i = 0; i < pageSize; i++) {
            shortcut.add(nonPrivate.get(i));
        }
        getHours(shortcut);
        return shortcut;
    }

    @Override
    public Page<Status> findAllByAccountIdPagination(Long id, Long pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize.intValue());
        return statusRepository.findAllByAccountIdOrderByPostedTimeDesc(id, pageable);
    }

    public Iterable<Status> getHours(Iterable<Status> statuses) {
        List<Status> statuses1 = (List<Status>) statuses;
        for (Status x : statuses1) {
            Long date = (new Date().getTime() - x.getPostedTime().getTime()) / 1000 / 60;
            x.setTime(date + "");
        }
        return statuses1;
    }
}
