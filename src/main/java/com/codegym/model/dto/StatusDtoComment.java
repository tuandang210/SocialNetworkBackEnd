package com.codegym.model.dto;

import com.codegym.model.account.Account;
import com.codegym.model.account.Privacy;
import com.codegym.model.comment.Comment;
import com.codegym.model.image.ImageStatus;
import com.codegym.model.like.LikeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDtoComment {

    private Long id;

    private String content;

    private Account account;

    private Privacy privacy;

    private Set<ImageStatus> imageStatuses;

    private List<Comment> comments;

    private List<LikeStatus> likeStatuses;

    private Date postedTime;

    private String time;
}
