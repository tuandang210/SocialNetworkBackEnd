package com.codegym.model.group;

import com.codegym.model.account.Account;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;


@Entity
@Data
@Table(name = "Groupss")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Account> account;

}
