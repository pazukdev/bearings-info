package com.pazukdev.backend.dto.product.seal;

import com.pazukdev.backend.dto.product.AbstractProductDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SealDto extends AbstractProductDto {

    private final static long serialVersionUID = 12343L;

    private String rotation;
    private String material;

}
