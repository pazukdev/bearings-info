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
@Table(name = "user_action")
public class UserAction extends AbstractEntity {

    private String userId = "-";
    private String userName = "-";
    private String userRole = "-";
    private String actionDate = "-";
    private String actionType = "-";
    private String parentItemId = "-";
    private String parentItemName = "-";
    private String oldItemId = "-";
    private String oldItemName = "-";
    private String itemId = "-";
    private String itemType = "-";
    private String itemCategory = "-";
    private String itemName = "-";

}
