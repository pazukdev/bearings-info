package com.pazukdev.backend.dto.motorcycle;

import com.pazukdev.backend.dto.abstraction.AbstractDto;
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

    private Integer weightG;
    private Long manufacturerId;

}
