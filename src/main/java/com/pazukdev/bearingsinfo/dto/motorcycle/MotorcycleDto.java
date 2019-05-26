package com.pazukdev.bearingsinfo.dto.motorcycle;

import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDto;
import lombok.Data;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class MotorcycleDto extends AbstractDto {

    private final static long serialVersionUID = 12343L;

    private String manufacturer;
    private Integer weightG;

}
