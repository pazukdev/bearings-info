package com.pazukdev.backend.entity.product.unit;

import com.pazukdev.backend.entity.product.Oil;
import com.pazukdev.backend.entity.product.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@MappedSuperclass
public class Unit extends Product {

    @Column(name = "oil_id")
    private Oil oil;

}
