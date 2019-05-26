package com.pazukdev.bearingsinfo.dto.bearing;

import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class BearingDto extends AbstractDto {

    private final static long serialVersionUID = 12343L;

    private String type;
    private String rollingElement;
    private Integer rollingElementsQuantity;

}
