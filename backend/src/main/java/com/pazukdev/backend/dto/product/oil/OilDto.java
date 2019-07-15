package com.pazukdev.backend.dto.product.oil;

import com.pazukdev.backend.dto.product.ProductDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OilDto extends ProductDto {

    private String base;
    private String viscosity;
    private String seasonality;

}
