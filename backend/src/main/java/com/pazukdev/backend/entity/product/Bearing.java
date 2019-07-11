package com.pazukdev.backend.entity.product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "bearing")
public class Bearing extends AbstractProduct {

    @Column(name = "type")
    private String type;
    @Column(name = "rolling_element")
    private String rollingElement;
    @Column(name = "rolling_elements_quantity")
    private Integer rollingElementsQuantity;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "bearings")
    private Set<Motorcycle> motorcycles;

}
