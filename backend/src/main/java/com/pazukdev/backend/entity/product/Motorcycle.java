package com.pazukdev.backend.entity.product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "motorcycle")
public class Motorcycle extends AbstractProduct {

    @Column(name = "weight_g")
    private Integer weightG;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "motorcycle_bearing",
            joinColumns = @JoinColumn(name = "motorcycle_id"),
            inverseJoinColumns = @JoinColumn(name = "bearing_id")
    )
    private Set<Bearing> bearings = new HashSet<>();

}
