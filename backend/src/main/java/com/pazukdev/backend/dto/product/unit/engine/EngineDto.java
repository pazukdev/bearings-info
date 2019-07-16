package com.pazukdev.backend.dto.product.unit.engine;

import com.pazukdev.backend.dto.product.unit.UnitDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EngineDto extends UnitDto {

    private Integer powerHp;
    private Integer torqueNm;
    private Integer speedRpm;
    private Long sparkPlugId;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Long> bearingIds = new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Long> oilIds = new HashSet<>();

}
