package com.codegym.repository;

import com.codegym.model.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IChatRepository extends JpaRepository<Chat,Long> {
    Optional<Chat> findChatByAccount1_IdAndAccount2_Id(Long id1,Long id2);

    Iterable<Chat> findChatsByAccount1_IdAndAccount2_Id(Long account1_id, Long account2_id);
}
