package com.pazukdev.backend.dto.product.unit;

import com.pazukdev.backend.dto.product.ProductDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UnitDto extends ProductDto {}
