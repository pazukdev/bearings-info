package com.pazukdev.backend.dto.manufacturer;

import com.pazukdev.backend.dto.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ManufacturerDto extends AbstractDto {

    private final static long serialVersionUID = 12343L;

    private String founded;
    private String defunct;
    private Set<Long> productsIds = new HashSet<>();

}
