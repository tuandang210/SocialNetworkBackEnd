package com.codegym.model.friend;

import com.codegym.model.account.Account;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account1;
    @ManyToOne
    private Account account2;
    @ManyToOne
    private FriendStatus friendStatus;

    public AccountRelation(Account account1, Account account2, FriendStatus friendStatus) {
        this.account1 = account1;
        this.account2 = account2;
        this.friendStatus = friendStatus;
    }
}
