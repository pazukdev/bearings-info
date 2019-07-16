package com.pazukdev.backend.entity.product;

import com.pazukdev.backend.entity.product.unit.Engine;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "motorcycle")
public class Motorcycle extends Product {

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;
    @Column(name = "weight_g")
    private Integer weightG;

}
