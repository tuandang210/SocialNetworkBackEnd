package com.codegym.repository;

import com.codegym.model.enumeration.EFriendStatus;
import com.codegym.model.friend.AccountRelation;
import com.codegym.model.friend.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Repository
public interface IAccountRelationRepository extends JpaRepository<AccountRelation, Long> {
    Optional<AccountRelation> findByAccount1_IdAndAccount2_Id(Long id1, Long id2);

    Iterable<AccountRelation> findAllByAccount1_IdOrAccount2_Id(Long id1, Long id2);

    Iterable<AccountRelation> findAllByAccount1_IdAndFriendStatus_Status(Long id1, EFriendStatus status);

    Iterable<AccountRelation> findAllByAccount2_IdAndFriendStatus_Status(Long id2, EFriendStatus status);

    Iterable<AccountRelation>findAllByAccount1_IdAndAccount2_IdAndFriendStatus_Status(Long id1, Long id2, EFriendStatus status);

    Iterable<AccountRelation> findAllByAccount1UsernameContainingAndAccount1IdAndFriendStatus_Status(@NotEmpty String account1_username, Long account1_id, EFriendStatus friendStatus_status);
    Iterable<AccountRelation> findAllByAccount1UsernameContainingAndAccount2IdAndFriendStatus_Status( String account1_username, Long account2_id, EFriendStatus friendStatus_status);
}
