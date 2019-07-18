package com.pazukdev.backend.entity.product.valve;

import com.pazukdev.backend.entity.product.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
//@Entity
//@Table(name = "valve")
public class Valve extends Product {

    @Column(name = "type")
    private String type;
    @Column(name = "diameter")
    private String diameter;

}
