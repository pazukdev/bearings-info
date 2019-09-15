package com.pazukdev.backend.dto.item;

import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.entity.item.TransitiveItem;
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

    private TransitiveItem item;
    private String location;
    private String quantity;

}
