package com.pazukdev.backend.dto.product;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.dto.AbstractDtoFactory;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDto;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.service.ManufacturerService;
import com.pazukdev.backend.tablemodel.TableRow;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Siarhei Sviarkaltsau
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ProductDtoFactory<Dto extends ProductDto> extends AbstractDtoFactory<Dto> {

    protected final ServiceContext context;
    protected final ManufacturerDtoFactory manufacturerDtoFactory;

    protected void applyProductionStartYear(final ProductDto dto, final TableRow tableRow) {
        final Integer productionStartYear = tableRow.getProductionStartYear(Characteristic.PRODUCTION);
        dto.setProductionStartYear(productionStartYear);
    }

    protected void applyProductionStopYear(final ProductDto dto, final TableRow tableRow) {
        final Integer productionStopYear = tableRow.getProductionStopYear(Characteristic.PRODUCTION);
        dto.setProductionStopYear(productionStopYear);
    }

    protected void applyManufacturer(final ProductDto dto, final TableRow tableRow) {
        final String manufacturerName = tableRow.getStringValue(Characteristic.MANUFACTURER);
        final ManufacturerService manufacturerService = context != null ? context.getManufacturerService() : null;
        final ManufacturerDto manufacturerDto = getDto(manufacturerName, manufacturerService, manufacturerDtoFactory);

        dto.setManufacturerId(manufacturerDto.getId());
    }

}
