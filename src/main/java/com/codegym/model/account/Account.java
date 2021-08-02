package com.codegym.model.account;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    @NotNull
    private String username;
    //    @Size(min = 6, max = 32)
//    @NotNull
    private String password;
    private String email;
    private String phone;
    private String birthday;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    private String avatar;
    private String fullName;
    private String address;
    private String favorite;
    private boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;

        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return 2083479647;
    }
}
