package com.codegym.model.image;

import com.codegym.model.status.Status;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
public class ImageStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    public ImageStatus(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public ImageStatus() {
    }

    public ImageStatus(String imageStatuses) {
        this.url = imageStatuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ImageStatus that = (ImageStatus) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 1503940477;
    }
}
