package com.pazukdev.backend.dto.item;

import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.entity.item.ItemEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemQuantity extends AbstractDto {

    private ItemEntity item;
    private String location;
    private Integer quantity;

}
