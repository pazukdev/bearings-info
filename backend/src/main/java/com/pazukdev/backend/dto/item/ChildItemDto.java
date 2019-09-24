package com.pazukdev.backend.dto.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChildItemDto extends ChildDto {

    private String location = "-";
    private String quantity = "0";

}
