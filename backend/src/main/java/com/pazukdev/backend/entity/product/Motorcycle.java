package com.pazukdev.backend.entity.product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@Entity
@Table(name = "motorcycle")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Motorcycle extends AbstractProduct {

    @Column(name = "weight_g")
    private Integer weightG;
    @OneToMany
    @JoinColumn(name = "id")
    private Set<Bearing> bearings;

}
