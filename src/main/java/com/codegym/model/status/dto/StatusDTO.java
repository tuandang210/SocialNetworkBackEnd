package com.codegym.model.status.dto;

import com.codegym.model.account.Account;
import com.codegym.model.account.Privacy;
import com.codegym.model.image.ImageStatus;
import com.codegym.model.like.LikeStatus;
import com.codegym.model.page.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class StatusDTO {
    private Long id;
    private String content;
    private Account account;
    private Privacy privacy;

    private Set<ImageStatus> imageStatuses;

    private String time;

    private Page page;

    private List<LikeStatus> likeStatus;

    public StatusDTO() {
    }

    public StatusDTO(Long id, String content, Account account, Privacy privacy, Set<ImageStatus> imageStatuses, String time, Page page, List<LikeStatus> likeStatus) {
        this.id = id;
        this.content = content;
        this.account = account;
        this.privacy = privacy;
        this.imageStatuses = imageStatuses;
        this.time = time;
        this.page = page;
        this.likeStatus = likeStatus;
    }
}
