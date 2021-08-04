package com.codegym.service.status;

import com.codegym.model.account.Account;
import com.codegym.model.status.Status;
import com.codegym.service.IGeneralService;
import org.springframework.stereotype.Service;

@Service
public interface IStatusService extends IGeneralService<Status> {

    Iterable<Status> findAllPublicStatus(Long id);

    Iterable<Status> findAllFriendStatus(Long id);

    Iterable<Status> findAllNonPrivateStatusByMyself(Long id);

    Iterable<Status> findAllStatusInNewsFeed(Long id);

    Iterable<Status> findAllByAccountId(Long id);
}
