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
import com.codegym.service.account.IAccountService;
import com.codegym.service.accountRelation.IAccountRelationService;
import com.codegym.service.comment.ICommentService;
import com.codegym.service.likeStatus.ILikeStatusService;
import com.codegym.service.status.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService{
    @Autowired
    private INotificationRepository notificationRepository;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAccountRelationService accountRelationService;

    @Autowired
    private IStatusService statusService;

    @Autowired
    private ILikeStatusService likeStatusService;

    @Autowired
    private ICommentService commentService;

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
        Status status = statusService.findById(like.getStatus().getId()).get();

        Account author = status.getAccount();
        Account liker = accountService.findById(like.getAccount().getId()).get();
        if (author.equals(liker)) {
            return null;
        }

        AccountRelation relation = accountRelationService.findByTwoAccountIds(author.getId(), liker.getId()).get();
        if (!relation.getFriendStatus().getStatus().equals(EFriendStatus.FRIEND)) {
            return null;
        }

        Notification likeNoti = new Notification();
        likeNoti.setAccount(author);
        likeNoti.setStatus(status);
        likeNoti.setLike(like);
        likeNoti.setContent(liker.getUsername() + " đã thích bài viết của bạn");
        return notificationRepository.save(likeNoti);
    }


    @Override
    public Notification saveCommentNotification(Comment comment) {
        Status status = statusService.findById(comment.getStatus().getId()).get();
        Account author = status.getAccount();
        Account commenter = accountService.findById(comment.getAccount().getId()).get();
        if (author.equals(commenter)) {
            return null;
        }

        AccountRelation relation = accountRelationService.findByTwoAccountIds(author.getId(), commenter.getId()).get();
        if (!relation.getFriendStatus().getStatus().equals(EFriendStatus.FRIEND)) {
            return null;
        }

        Notification commentNoti = new Notification();
        commentNoti.setAccount(author);
        commentNoti.setComment(comment);
        commentNoti.setStatus(status);
        commentNoti.setContent(commenter.getUsername() + " đã bình luận về bài viết của bạn");
        return notificationRepository.save(commentNoti);
    }


    @Override
    public Iterable<Notification> saveStatusNotification(Status status) {
        Status savedStatus = statusService.findById(status.getId()).get();
        if (status.getPrivacy().getId() == 3){
            return null;
        }

        Account author = accountService.findById(savedStatus.getAccount().getId()).get();
        Iterable<Account> friends = accountRelationService.findAllByAccountIdAndStatus(author.getId(), EFriendStatus.FRIEND);
        if (!friends.iterator().hasNext()) {
            return null;
        }
        List<Notification> notiList = new ArrayList<>();
        for (Account friend: friends) {
            Notification statusNoti = new Notification();
            statusNoti.setAccount(friend);
            statusNoti.setStatus(savedStatus);
            statusNoti.setContent(author.getUsername() + " đã đăng một bài viết mới");
            notiList.add(notificationRepository.save(statusNoti));
        }
        return notiList;
    }

    @Override
    public Optional<Notification> findLikeNotification(Long accountId, Long likeId) {
        return notificationRepository.findAllByAccount_IdAndLike_Id(accountId, likeId);
    }

    @Override
    public Optional<Notification> findCommentNotification(Long accountId, Long commentId) {
        return notificationRepository.findAllByAccount_IdAndComment_Id(accountId, commentId);
    }

    @Override
    public Optional<Notification> findStatusNotification(Long accountId, Long statusId) {
        return notificationRepository.findAllByAccount_IdAndStatus_Id(accountId, statusId);
    }

    @Override
    public void deleteLikeNotification(LikeStatus like) {
        LikeStatus receivedLike = likeStatusService.findById(like.getId()).get();
        if (receivedLike.getAccount().equals(receivedLike.getStatus().getAccount())){
            return;
        }
        Notification deletedNoti = notificationRepository.findAllByAccount_IdAndLike_Id(
                receivedLike.getStatus().getAccount().getId()
                , receivedLike.getId()).get();
        if (deletedNoti != null){
            notificationRepository.deleteById(deletedNoti.getId());
        }
    }

    @Override
    public void deleteCommentNotification(Comment comment) {
        Comment receivedComment = commentService.findById(comment.getId()).get();
        if (receivedComment.getAccount().equals(receivedComment.getStatus().getAccount())) {
            return;
        }

        Notification deletedCommentNoti = notificationRepository.findAllByAccount_IdAndComment_Id(
                receivedComment.getStatus().getAccount().getId()
                , receivedComment.getId()).get();
        if (deletedCommentNoti != null) {
            notificationRepository.deleteById(deletedCommentNoti.getId());
        }
    }

    @Override
    public void deleteStatusNotification(Status status) {
        Status receivedStatus = statusService.findById(status.getId()).get();
        Account author = receivedStatus.getAccount();
//        Iterable<Account> friends = accountRelationService.findAllByAccountIdAndStatus(author.getId(), EFriendStatus.FRIEND);
//        if (!friends.iterator().hasNext()) {
//            return;
//        }
//        for (Account friend: friends) {
//            Notification deletedStatusNoti= notificationRepository.findAllByAccount_IdAndStatus_Id(
//                    friend.getId(), receivedStatus.getId()).get();
//            notificationRepository.deleteById(deletedStatusNoti.getId());
//        }
        Iterable<Notification> relatedNotis = notificationRepository.findAllByStatus_Id(receivedStatus.getId());
        if (!relatedNotis.iterator().hasNext()){
            return;
        }
        for (Notification noti: relatedNotis) {
            notificationRepository.deleteById(noti.getId());
        }
    }
}
