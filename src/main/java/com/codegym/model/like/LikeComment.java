package com.codegym.model.like;

import com.codegym.model.account.Account;
import com.codegym.model.comment.Comment;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class LikeComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isLike;
    @OneToOne
    private Account account;
    @ManyToOne
    private Comment comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LikeComment that = (LikeComment) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 1564166827;
    }
}
