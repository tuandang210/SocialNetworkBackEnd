package com.codegym.model.chat;

import com.codegym.model.account.Account;
import com.codegym.model.group.Group;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private Account account1;

    @ManyToOne
    private Account account2;

    private String date;

    @ManyToOne
    private Group group;
}
