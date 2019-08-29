package com.pazukdev.backend.dto.item;

import com.pazukdev.backend.dto.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemDto extends AbstractDto {

    private String category;
    private Integer quantity;
    private String description;
    private String replacer;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Long> itemIds = new HashSet<>();

}
