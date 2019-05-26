package com.pazukdev.bearingsinfo.dto.seal;

import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDto;
import lombok.Data;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class SealDto extends AbstractDto {

    private final static long serialVersionUID = 12343L;

    private String rotation;
    private String material;

}
