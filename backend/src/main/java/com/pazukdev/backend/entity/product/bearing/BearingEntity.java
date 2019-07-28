package com.pazukdev.backend.entity.product.bearing;

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
@Table(name = "bearing")
public class BearingEntity extends Product {

    @Column(name = "type")
    private String type;
    @Column(name = "rolling_element")
    private String rollingElement;
    @Column(name = "rolling_elements_quantity")
    private Integer rollingElementsQuantity;

}
