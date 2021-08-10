package com.codegym.repository;

import com.codegym.model.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
    //tìm các noti chưa đọc của 1 người
    Iterable<Notification> findAllByIsReadFalseAndAccount_IdOrderByCreateDateDesc(Long id);

    //tìm tất cả các noti đã đọc của 1 người
    Iterable<Notification> findAllByIsReadTrueAndAccount_IdOrderByCreateDateDesc(Long id);

    //tìm tất cả các noti của 1 người
    Iterable<Notification> findAllByAccount_IdOrderByCreateDateDesc(Long id);
}
