package com.pazukdev.backend.dto.product;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.config.ServiceContext;
import com.pazukdev.backend.dto.AbstractDto;
import com.pazukdev.backend.dto.AbstractDtoFactory;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDto;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.search.DefaultSearchRequest;
import com.pazukdev.backend.service.AbstractService;
import com.pazukdev.backend.service.ManufacturerService;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Siarhei Sviarkaltsau
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractProductDtoFactory<Dto extends AbstractProductDto> extends AbstractDtoFactory<Dto> {

    protected final ServiceContext context;
    protected final ManufacturerDtoFactory manufacturerDtoFactory;

    protected void applyProductionStartYear(final AbstractProductDto dto, final TableRow tableRow) {
        final Integer productionStartYear = tableRow.getProductionStartYear(Characteristic.PRODUCTION);
        dto.setProductionStartYear(productionStartYear);
    }

    protected void applyProductionStopYear(final AbstractProductDto dto, final TableRow tableRow) {
        final Integer productionStopYear = tableRow.getProductionStopYear(Characteristic.PRODUCTION);
        dto.setProductionStopYear(productionStopYear);
    }

    protected void applyManufacturer(final AbstractProductDto dto, final TableRow tableRow) {
        final String manufacturerName = tableRow.getStringValue(Characteristic.MANUFACTURER);
        final ManufacturerService manufacturerService = context != null ? context.getManufacturerService() : null;
        final ManufacturerDto manufacturerDto = getDto(manufacturerName, manufacturerService, manufacturerDtoFactory);

        dto.setManufacturerId(manufacturerDto.getId());
    }

    @SuppressWarnings("unchecked")
    protected <Dto extends AbstractDto> Dto getDto(final String name,
                                                   final AbstractService service,
                                                   final AbstractDtoFactory<Dto> dtoFactory) {
        final DefaultSearchRequest request = new DefaultSearchRequest();
        request.setName(name);

        final Dto dto;
        if (service != null) {
            dto = (Dto) service.search(request);
        } else {
            dto = CSVFileUtil.searchByName(request, dtoFactory);
        }
        return dto;
    }

}
