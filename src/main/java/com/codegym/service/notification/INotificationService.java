package com.codegym.service.notification;

import com.codegym.model.comment.Comment;
import com.codegym.model.like.LikeStatus;
import com.codegym.model.notification.Notification;
import com.codegym.model.status.Status;
import com.codegym.service.IGeneralService;

import java.util.Optional;

public interface INotificationService extends IGeneralService<Notification> {
    // hiển thị
    Iterable<Notification> findAllUnreadByAccountId(Long id);

    Iterable<Notification> findAllReadByAccountId(Long id);

    Iterable<Notification> findAllByAccountId(Long id);

    // lưu thông báo
    Notification saveLikeNotification (LikeStatus like);

    Notification saveCommentNotification (Comment comment);

    Iterable<Notification> saveStatusNotification (Status status);

    // tìm thông báo
    Optional<Notification> findLikeNotification(Long accountId, Long likeId);

    Optional<Notification> findCommentNotification(Long accountId, Long commentId);

    Optional<Notification> findStatusNotification(Long accountId, Long statusId);

    //Xóa thông báo
    void deleteLikeNotification(LikeStatus like);

    void deleteCommentNotification(Comment comment);

    void deleteStatusNotification(Status status);
}
