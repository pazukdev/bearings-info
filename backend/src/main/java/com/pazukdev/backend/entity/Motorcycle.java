package com.pazukdev.backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @Column(name = "weight_g")
    private Integer weightG;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

}
