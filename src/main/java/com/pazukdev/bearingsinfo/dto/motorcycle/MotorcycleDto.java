package com.pazukdev.bearingsinfo.dto.motorcycle;

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
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MotorcycleDto extends AbstractDto {

    private final static long serialVersionUID = 12343L;

    private String manufacturer;
    private Integer weightG;

}
