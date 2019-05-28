package com.pazukdev.bearingsinfo.dto.bearing;

import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDto;
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
public class BearingDto extends AbstractDto {

    private final static long serialVersionUID = 12343L;

    private String type;
    private String rollingElement;
    private Integer rollingElementsQuantity;

}
