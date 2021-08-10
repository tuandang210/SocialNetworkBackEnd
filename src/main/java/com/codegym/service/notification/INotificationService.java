package com.codegym.service.notification;

import com.codegym.model.notification.Notification;
import com.codegym.service.IGeneralService;

public interface INotificationService extends IGeneralService<Notification> {
    Iterable<Notification> findAllUnreadByAccountId(Long id);

    Iterable<Notification> findAllReadByAccountId(Long id);

    Iterable<Notification> findAllByAccountId(Long id);
}
