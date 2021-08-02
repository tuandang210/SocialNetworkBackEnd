package com.codegym.model.notification;

import com.codegym.model.friend.AccountRelation;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class NotificationFriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private AccountRelation accountRelation;
    private boolean isNew = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NotificationFriendRequest that = (NotificationFriendRequest) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 819056016;
    }
}
