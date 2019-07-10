package com.pazukdev.backend.dto.product.motorcycle;

import com.pazukdev.backend.dto.product.AbstractProductDto;
import com.pazukdev.backend.dto.product.bearing.BearingDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class MotorcycleDto extends AbstractProductDto {

    private final static long serialVersionUID = 12343L;

    private Integer weightG;
    private Set<BearingDto> bearingDtos;

}
