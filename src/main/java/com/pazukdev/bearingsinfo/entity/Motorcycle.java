package com.pazukdev.bearingsinfo.entity;

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
@Table(name = "motorcycle")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Motorcycle extends AbstractEntity {

    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "weight_g")
    private Integer weightG;

}