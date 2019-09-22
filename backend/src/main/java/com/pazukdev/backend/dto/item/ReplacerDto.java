package com.pazukdev.backend.dto.item;

import com.pazukdev.backend.dto.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReplacerDto extends AbstractDto {

    private Long itemId;
    private String itemName;
    private String buttonText;
    private String selectText;
    private String comment;

}
