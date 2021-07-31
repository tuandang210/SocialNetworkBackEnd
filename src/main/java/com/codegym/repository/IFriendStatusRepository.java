package com.codegym.repository;

import com.codegym.model.friend.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFriendStatusRepository extends JpaRepository<FriendStatus, Long> {
}
