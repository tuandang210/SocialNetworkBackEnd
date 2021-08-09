package com.codegym.service.chat;

import com.codegym.model.chat.Chat;
import com.codegym.model.group.Group;
import com.codegym.service.IGeneralService;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IChatService extends IGeneralService<Chat> {
    List<Chat> findAll(int page, int size);

    Optional<Chat> findChatByAccount1_IdAndAccount2_Id(Long id1, Long id2);

    Iterable<Chat> findChatsByAccount1_IdAndAccount2_Id(Long account1_id, Long account2_id);
    Iterable<Chat> findChatsByGroup_IdOrderByDateAsc(Long groupId, int size);
}
