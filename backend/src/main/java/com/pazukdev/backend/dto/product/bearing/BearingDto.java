package com.pazukdev.backend.dto.product.bearing;

import com.pazukdev.backend.dto.product.AbstractProductDto;
import com.pazukdev.backend.dto.product.motorcycle.MotorcycleDto;
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
public class BearingDto extends AbstractProductDto {

    private final static long serialVersionUID = 12343L;

    private String type;
    private String rollingElement;
    private Integer rollingElementsQuantity;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<MotorcycleDto> motorcycles = new HashSet<>();

}
