package com.codegym.repository;

import com.codegym.model.enumeration.EFriendStatus;
import com.codegym.model.friend.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFriendStatusRepository extends JpaRepository<FriendStatus, Long> {
    Optional<FriendStatus> findByStatus(EFriendStatus status);
}
