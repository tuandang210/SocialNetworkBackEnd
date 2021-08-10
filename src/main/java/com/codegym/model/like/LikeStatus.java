package com.codegym.model.like;

import com.codegym.model.account.Account;
import com.codegym.model.status.Status;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class LikeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isLike = false;
    @OneToOne
    private Account account;
    @ManyToOne
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LikeStatus that = (LikeStatus) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 1156716295;
    }
}
