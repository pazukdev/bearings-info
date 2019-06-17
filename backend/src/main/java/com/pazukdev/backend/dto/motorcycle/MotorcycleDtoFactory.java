package com.pazukdev.backend.dto.motorcycle;

import com.pazukdev.backend.characteristic.Characteristic;
import com.pazukdev.backend.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDto;
import com.pazukdev.backend.dto.manufacturer.ManufacturerDtoFactory;
import com.pazukdev.backend.entity.Manufacturer;
import com.pazukdev.backend.search.DefaultSearchRequest;
import com.pazukdev.backend.service.ManufacturerService;
import com.pazukdev.backend.tablemodel.TableRow;
import com.pazukdev.backend.util.CSVFileUtil;
import com.pazukdev.backend.util.WeightUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Component
public class MotorcycleDtoFactory extends AbstractDtoFactory<MotorcycleDto> {

    private final ManufacturerService service;

    @Override
    protected File getCSVFile() {
        return CSVFileUtil.motorcycleFile();
    }

    @Override
    public MotorcycleDto createDto() {
        return new MotorcycleDto();
    }

    @Override
    protected void applyCharacteristics(MotorcycleDto dto, TableRow tableRow) {
        applyName(dto, tableRow);
        applyManufacturer(dto, tableRow);
        applyWeight(dto, tableRow);
    }

    private void applyManufacturer(final MotorcycleDto dto, final TableRow tableRow) {
        final String manufacturerName = tableRow.getStringValue(Characteristic.MANUFACTURER);

        final ManufacturerDto manufacturerDto;
        if (service != null) {
            final DefaultSearchRequest request = new DefaultSearchRequest();
            request.setName(manufacturerName);
            manufacturerDto = service.search(request);
        } else {
            manufacturerDto = new ManufacturerDtoFactory().searchByName(manufacturerName);
        }

        dto.setManufacturerId(manufacturerDto.getId());
    }

    private void applyWeight(final MotorcycleDto dto, final TableRow tableRow) {
        final Integer weight_kg = tableRow.getIntegerValue(Characteristic.WEIGHT_KG);
        dto.setWeightG(WeightUtil.toG(weight_kg));
    }

}
