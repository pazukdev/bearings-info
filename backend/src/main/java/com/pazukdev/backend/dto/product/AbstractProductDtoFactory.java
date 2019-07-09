package com.pazukdev.backend.dto.product;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.dto.AbstractDtoFactory;
import com.pazukdev.backend.tablemodel.TableRow;

/**
 * @author Siarhei Sviarkaltsau
 */
public abstract class AbstractProductDtoFactory<Dto extends AbstractProductDto> extends AbstractDtoFactory<Dto> {

    protected void applyProductionStartYear(final AbstractProductDto dto, final TableRow tableRow) {
        final Integer productionStartYear = tableRow.getProductionStartYear(Characteristic.PRODUCTION);
        dto.setProductionStartYear(productionStartYear);
    }

    protected void applyProductionStopYear(final AbstractProductDto dto, final TableRow tableRow) {
        final Integer productionStopYear = tableRow.getProductionStopYear(Characteristic.PRODUCTION);
        dto.setProductionStopYear(productionStopYear);
    }

}
