package com.codegym.model.notification;

import com.codegym.model.account.Account;
import com.codegym.model.comment.Comment;
import com.codegym.model.like.LikeStatus;
import com.codegym.model.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account;

    private String content;

    private Date createDate = new Date();

    private Boolean isRead = false;

    //Bài đăng của bạn bè
    @ManyToOne(cascade = CascadeType.ALL)
    private Status status;

    //Bạn bè comment trên bài của mình
    @ManyToOne(cascade = CascadeType.ALL)
    private Comment comment;

    //Bạn bè like bài của mình
    @ManyToOne(cascade = CascadeType.ALL)
    private LikeStatus like;
}
