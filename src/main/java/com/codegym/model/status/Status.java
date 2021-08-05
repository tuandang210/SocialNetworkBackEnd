package com.codegym.model.status;

import com.codegym.model.account.Account;
import com.codegym.model.account.Privacy;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

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

    private Date postedTime = new Date();

    private String time;
}
