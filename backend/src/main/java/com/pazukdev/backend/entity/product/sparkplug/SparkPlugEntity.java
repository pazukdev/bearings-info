package com.pazukdev.backend.entity.product.sparkplug;

import com.pazukdev.backend.entity.product.Product;
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
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "spark_plug")
public class SparkPlugEntity extends Product {

    @Column(name = "heat_range")
    private Integer heatRange;

}
