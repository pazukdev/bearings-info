package com.pazukdev.backend.entity;

import com.pazukdev.backend.entity.item.ItemEntity;
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
public class WishListEntity extends AbstractEntity {

    @OneToMany
    @JoinTable(
            name = "wishlist_item",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private Set<ItemEntity> items = new HashSet<>();

}
