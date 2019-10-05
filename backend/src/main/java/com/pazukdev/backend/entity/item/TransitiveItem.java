package com.pazukdev.backend.entity.item;

import com.pazukdev.backend.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "transitive_item")
public class TransitiveItem extends AbstractEntity {

    private String category;
    private String description = "-";
    private String replacer = "-";

}