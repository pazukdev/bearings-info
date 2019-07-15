package com.pazukdev.backend.dto.product.motorcycle;

import com.pazukdev.backend.dto.product.ProductDto;
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
public class MotorcycleDto extends ProductDto {

    private final static long serialVersionUID = 12343L;

    private Integer weightG;
    private Set<Long> bearingIds = new HashSet<>();

}
