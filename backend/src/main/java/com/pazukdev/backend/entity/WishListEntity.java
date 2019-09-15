package com.pazukdev.backend.entity;

import com.pazukdev.backend.entity.item.TransitiveItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
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
public class WishListEntity extends AbstractEntity {

    @OneToMany
    @JoinTable(
            name = "wishlist_item",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private Set<TransitiveItem> items = new HashSet<>();

}
