package com.codegym.model.status;

import com.codegym.model.account.Account;
import com.codegym.model.account.Privacy;
import com.codegym.model.image.ImageStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne
    private Account account;
    @ManyToOne
    private Privacy privacy;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<ImageStatus> imageStatuses;

    private Date postedTime = new Date();

    private String time;
}
