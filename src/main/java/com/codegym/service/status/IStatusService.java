package com.codegym.service.status;

import com.codegym.model.account.Account;
import com.codegym.model.status.Status;
import com.codegym.service.IGeneralService;
import org.springframework.stereotype.Service;

@Service
public interface IStatusService extends IGeneralService<Status> {

    Iterable<Status> findAllPublicStatus();

    Iterable<Status> findAllFriendStatus(Long id);

    Iterable<Status> findALlStatusInNewsFeed(Long id);
}
