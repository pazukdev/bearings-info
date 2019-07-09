package com.pazukdev.backend.dto.product;

import com.pazukdev.backend.dto.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AbstractProductDto extends AbstractDto {

    private final static long serialVersionUID = 12343L;

    private Long manufacturerId;
    private Integer productionStartYear;
    private Integer productionStopYear;

}
