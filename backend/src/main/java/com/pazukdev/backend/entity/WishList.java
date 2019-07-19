package com.pazukdev.backend.entity;

import com.pazukdev.backend.entity.product.bearing.Bearing;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
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
@Table(name = "wishlist")
public class WishList extends AbstractEntity {

    @OneToMany
    @JoinTable(
            name = "wishlist_bearing",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "bearing_id")
    )
    private Set<Bearing> bearings = new HashSet<>();

}
