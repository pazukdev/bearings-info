package com.pazukdev.backend.dto.product.bearing;

import com.pazukdev.backend.dto.product.AbstractProductDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

}
