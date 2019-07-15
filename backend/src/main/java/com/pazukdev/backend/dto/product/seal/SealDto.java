package com.pazukdev.backend.dto.product.seal;

import com.pazukdev.backend.dto.product.ProductDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SealDto extends ProductDto {

    private final static long serialVersionUID = 12343L;

    private String rotation;
    private String material;

}
