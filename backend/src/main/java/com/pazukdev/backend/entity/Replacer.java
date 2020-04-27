package com.pazukdev.backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "replacer")
public class Replacer extends AbstractEntity implements Childable {

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;
    private String comment = "-";

    @Override
    public String getType() {
        return "replacer";
    }

    @Override
    public String getDetails() {
        return "comment=" + comment;
    }

}
