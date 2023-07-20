package com.wagner.store.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    private Date createdAt;
    private Date updatedAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;
}