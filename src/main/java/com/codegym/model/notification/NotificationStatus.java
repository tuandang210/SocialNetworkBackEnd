package com.codegym.model.notification;

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
public class NotificationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Status status;
    private boolean isNew = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NotificationStatus that = (NotificationStatus) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 592485404;
    }
}
