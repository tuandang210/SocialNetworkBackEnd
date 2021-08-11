package com.codegym.model.page;

import com.codegym.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Page page;

    private int isAdmin;

    public MemberPage(Account account, Page page, int isAdmin) {
        this.account = account;
        this.page = page;
        this.isAdmin = isAdmin;
    }
}
