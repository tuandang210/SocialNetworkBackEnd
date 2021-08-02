package com.codegym.model.friend;

import com.codegym.model.enumeration.EFriendStatus;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EFriendStatus status;

    public FriendStatus(EFriendStatus status) {
        this.status = status;
    }
}
