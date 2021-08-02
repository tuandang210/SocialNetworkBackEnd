package com.codegym.service.friendStatus;


import com.codegym.model.enumeration.EFriendStatus;
import com.codegym.model.friend.FriendStatus;
import com.codegym.repository.IFriendStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendStatusService implements IFriendStatusService {
    @Autowired
    private IFriendStatusRepository friendStatusRepository;

    @Override
    public Iterable<FriendStatus> findAll() {
        return friendStatusRepository.findAll();
    }

    @Override
    public Optional<FriendStatus> findById(Long id) {
        return friendStatusRepository.findById(id);
    }

    @Override
    public FriendStatus save(FriendStatus friendStatus) {
        return friendStatusRepository.save(friendStatus);
    }

    @Override
    public void delete(Long id) {
        friendStatusRepository.deleteById(id);
    }

    @Override
    public Optional<FriendStatus> findByStatus(EFriendStatus status) {
        return friendStatusRepository.findByStatus(status);
    }
}
