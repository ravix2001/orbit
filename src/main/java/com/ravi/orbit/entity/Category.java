package com.ravi.orbit.entity;

import com.ravi.orbit.enums.EStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private EStatus status = EStatus.ACTIVE;

    @Column(name = "img_url")
    private String imgUrl;

}