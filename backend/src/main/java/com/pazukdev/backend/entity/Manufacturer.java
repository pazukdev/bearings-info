package com.pazukdev.backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@Entity
@Table(name = "manufacturer")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Manufacturer extends AbstractEntity {

    @Column(name = "founded")
    private String founded;
    @Column(name = "defunct")
    private String defunct;

}
