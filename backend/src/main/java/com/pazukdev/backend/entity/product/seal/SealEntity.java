package com.pazukdev.backend.entity.product.seal;

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
@Entity
@Table(name = "seal")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SealEntity extends Product {

    @Column(name = "rotation")
    private String rotation;
    @Column(name = "material")
    private String material;

}