package com.codegym.service.status;

import com.codegym.model.account.Account;
import com.codegym.model.status.Status;
import com.codegym.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface IStatusService extends IGeneralService<Status> {

    Iterable<Status> findAllPublicStatusByOther(Long id);

    Iterable<Status> findAllPublicStatusByMyself(Long id);

    Iterable<Status> findAllFriendStatus(Long id);

    Iterable<Status> findAllNonPrivateStatusByMyself(Long id);

    Iterable<Status> findAllStatusInNewsFeed(Long id);

    Iterable<Status> findAllByAccountId(Long id);

    // Bảng tin
    Iterable<Status> findAllStatusInNewsFeedPagination(Long id, Long pageSize);
    // Tìm tất cả bài đăng public của 1 cá nhân
    Iterable<Status> findAllPublicStatusByMyselfPagination(Long id, Long pageSize);
    // Tìm tất cả bài đăng public và friend-only của 1 cá nhân
    Iterable<Status> findAllNonPrivateStatusByMySelfPagination(Long id, Long pageSize);
    // Tìm tất cả bài đăng của 1 cá nhân
    Page<Status> findAllByAccountIdPagination(Long id, Long pageSize);
}
