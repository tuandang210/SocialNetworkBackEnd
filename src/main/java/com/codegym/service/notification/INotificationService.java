package com.codegym.service.notification;

import com.codegym.model.comment.Comment;
import com.codegym.model.like.LikeStatus;
import com.codegym.model.notification.Notification;
import com.codegym.model.status.Status;
import com.codegym.service.IGeneralService;

public interface INotificationService extends IGeneralService<Notification> {
    Iterable<Notification> findAllUnreadByAccountId(Long id);

    Iterable<Notification> findAllReadByAccountId(Long id);

    Iterable<Notification> findAllByAccountId(Long id);

    Notification saveLikeNotification (LikeStatus like);

    Notification saveCommentNotification (Comment comment);

    Iterable<Notification> saveStatusNotification (Status status);
}
