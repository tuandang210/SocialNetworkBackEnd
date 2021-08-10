package com.codegym.service.notification;

import com.codegym.model.account.Account;
import com.codegym.model.comment.Comment;
import com.codegym.model.dto.ResponseMessage;
import com.codegym.model.enumeration.EFriendStatus;
import com.codegym.model.friend.AccountRelation;
import com.codegym.model.like.LikeStatus;
import com.codegym.model.notification.Notification;
import com.codegym.model.status.Status;
import com.codegym.repository.INotificationRepository;
import com.codegym.service.accountRelation.IAccountRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService{
    @Autowired
    private INotificationRepository notificationRepository;

    @Autowired
    private IAccountRelationService accountRelationService;

    @Override
    public Iterable<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Notification save(Notification notification) {
        if (notification.getCreateDate() == null){
            notification.setCreateDate(new Date());
        }
        if (notification.getIsRead() == null){
            notification.setIsRead(false);
        }
        return notificationRepository.save(notification);
    }

    @Override
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public Iterable<Notification> findAllUnreadByAccountId(Long id) {
        return notificationRepository.findAllByIsReadFalseAndAccount_IdOrderByCreateDateDesc(id);
    }

    @Override
    public Iterable<Notification> findAllReadByAccountId(Long id) {
        return notificationRepository.findAllByIsReadTrueAndAccount_IdOrderByCreateDateDesc(id);
    }

    @Override
    public Iterable<Notification> findAllByAccountId(Long id) {
        return notificationRepository.findAllByAccount_IdOrderByCreateDateDesc(id);
    }

    @Override
    public Notification saveLikeNotification(LikeStatus like) {
        Account author = like.getStatus().getAccount();
        Account liker = like.getAccount();
        if (author.equals(liker)) {
            return null;
        }

        AccountRelation relation = accountRelationService.findByTwoAccountIds(author.getId(), liker.getId()).get();
        if (!relation.getFriendStatus().getStatus().equals(EFriendStatus.FRIEND)) {
            return null;
        }

        Notification likeNoti = new Notification();
        likeNoti.setAccount(author);
        likeNoti.setLike(like);
        likeNoti.setContent(liker.getUsername() + " đã thích bài viết của bạn");
        return notificationRepository.save(likeNoti);
    }

    @Override
    public Notification saveCommentNotification(Comment comment) {
        return null;
    }

    @Override
    public Iterable<Notification> saveStatusNotification(Status status) {
        return null;
    }
}
