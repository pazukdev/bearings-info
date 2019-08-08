package com.pazukdev.backend.dto.item;

import com.pazukdev.backend.dto.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemDto extends AbstractDto {

    private List<ItemDto> items;
    private List<ItemDescription> descriptions;

    public ItemDto(final String description) {
        this.descriptions = ItemDescriptionFactory.create(description);
    }

}
