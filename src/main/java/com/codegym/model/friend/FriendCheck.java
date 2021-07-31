package com.codegym.model.friend;

import com.codegym.model.account.Account;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class FriendCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private FriendStatus friendStatus;
    @ManyToOne
    private Account account1;
    @ManyToOne
    private Account account2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FriendCheck that = (FriendCheck) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 157103481;
    }
}
