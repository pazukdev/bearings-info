package com.pazukdev.backend.entity.product.oil;

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
@Table(name = "oil")
public class OilEntity extends Product {

    @Column(name = "base")
    private String base;
    @Column(name = "viscosity")
    private String viscosity;
    @Column(name = "seasonality")
    private String seasonality;

}
