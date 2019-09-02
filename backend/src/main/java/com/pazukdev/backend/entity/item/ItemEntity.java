package com.pazukdev.backend.entity.item;

import com.pazukdev.backend.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "item")
public class ItemEntity extends AbstractEntity {

    private String category;
    private Integer quantity;
    private String description;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "item_item_quantity",
            joinColumns = @JoinColumn(name = "parent_item_id"),
            inverseJoinColumns = @JoinColumn(name = "child_item_quantity_id")
    )
    private Set<ItemQuantity> itemQuantities = new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "item_replacer",
            joinColumns = @JoinColumn(name = "original_item_id"),
            inverseJoinColumns = @JoinColumn(name = "replacer_item_id")
    )
    private Set<Replacer> replacers = new HashSet<>();

}
