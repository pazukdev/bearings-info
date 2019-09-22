package com.pazukdev.backend.entity.item;

import com.pazukdev.backend.entity.AbstractEntity;
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
@Table(name = "item")
public class Item extends AbstractEntity {

    private String category;
    private String description;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "item_child_item",
            joinColumns = @JoinColumn(name = "parent_item_id"),
            inverseJoinColumns = @JoinColumn(name = "child_item_id")
    )
    private Set<ChildItem> childItems = new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "item_replacer",
            joinColumns = @JoinColumn(name = "original_item_id"),
            inverseJoinColumns = @JoinColumn(name = "replacer_item_id")
    )
    private Set<Replacer> replacers = new HashSet<>();
}
