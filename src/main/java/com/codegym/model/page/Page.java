package com.codegym.model.page;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "pages")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int privacy;
}
