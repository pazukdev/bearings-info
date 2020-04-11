package com.pazukdev.backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@MappedSuperclass
@EqualsAndHashCode
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name = "-";

    private String status = "active";

}
