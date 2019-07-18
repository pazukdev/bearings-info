package com.pazukdev.backend.dto.product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ValveDto extends ProductDto {

    private String type;
    private String diameter;

}
