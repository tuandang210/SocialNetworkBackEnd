package com.codegym.service.friendStatus;

import com.codegym.model.enumeration.EFriendStatus;
import com.codegym.model.friend.FriendStatus;
import com.codegym.service.IGeneralService;

import java.util.Optional;

public interface IFriendStatusService extends IGeneralService<FriendStatus> {
    Optional<FriendStatus> findByStatus(EFriendStatus status);
}
