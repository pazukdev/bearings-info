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
@Table(name = "bearing")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Bearing extends AbstractEntity {

    @Column(name = "type")
    private String type;
    @Column(name = "rolling_element")
    private String rollingElement;
    @Column(name = "rolling_elements_quantity")
    private Integer rollingElementsQuantity;

}
