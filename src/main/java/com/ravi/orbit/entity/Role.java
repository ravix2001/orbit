package com.ravi.orbit.entity;

import com.ravi.orbit.enums.ERole;
import com.ravi.orbit.enums.EStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", unique = true, nullable = false)
    private ERole role;

    @Column(name = "color_code")
    private String colorCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EStatus status;

}
