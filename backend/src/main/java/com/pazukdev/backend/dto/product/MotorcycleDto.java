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
public class MotorcycleDto extends ProductDto {

    private final static long serialVersionUID = 12343L;

    private Long engineId;
    private Integer weightG;

}
