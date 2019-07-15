package com.pazukdev.backend.entity.product;

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
