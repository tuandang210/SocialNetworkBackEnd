package com.codegym.service.chat;

import com.codegym.model.chat.Chat;
import com.codegym.repository.IChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Comparator.comparing;

@Service
public class ChatService implements IChatService {

    @Autowired
    private IChatRepository chatRepository;
//    private java.util.Comparator<? super Chat> Comparator;

    @Override
    public Iterable<Chat> findAll() {
        return chatRepository.findAll();
    }

    @Override
    public Optional<Chat> findById(Long id) {
        return chatRepository.findById(id);
    }

    @Override
    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public void delete(Long id) {
        chatRepository.findById(id);
    }

    @Override
    public List<Chat> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Chat> chatPage = chatRepository.findAll(pageRequest);
        return chatPage.getContent();
    }

    @Override
    public Optional<Chat> findChatByAccount1_IdAndAccount2_Id(Long id1, Long id2) {
        return chatRepository.findChatByAccount1_IdAndAccount2_Id(id1, id2);
    }

    @Override
    public Iterable<Chat> findChatsByAccount1_IdAndAccount2_Id(Long account1_id, Long account2_id) {
        List<Chat> chats1 = (List<Chat>) chatRepository.findChatsByAccount1_IdAndAccount2_Id(account1_id, account2_id);
        List<Chat> chats2 = (List<Chat>) chatRepository.findChatsByAccount1_IdAndAccount2_Id(account2_id, account1_id);
        chats1.addAll(chats2);
        chats1.sort(comparing(Chat::getId));
        return chats1;
    }
}
