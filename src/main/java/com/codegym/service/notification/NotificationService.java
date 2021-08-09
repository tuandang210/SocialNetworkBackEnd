package com.codegym.service.notification;

import com.codegym.model.notification.Notification;
import com.codegym.repository.INotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService{
    @Autowired
    private INotificationRepository notificationRepository;

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
}
