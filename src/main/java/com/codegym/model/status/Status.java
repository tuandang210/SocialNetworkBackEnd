package com.codegym.model.status;

import com.codegym.model.account.Account;
import com.codegym.model.account.Privacy;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne
    private Account account;
    @OneToOne
    private Privacy privacy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Status status = (Status) o;

        return Objects.equals(id, status.id);
    }

    @Override
    public int hashCode() {
        return 2026255630;
    }
}
