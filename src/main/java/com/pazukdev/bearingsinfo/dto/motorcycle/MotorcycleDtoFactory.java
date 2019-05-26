package com.pazukdev.bearingsinfo.dto.motorcycle;

import com.pazukdev.bearingsinfo.DataFileContent;
import com.pazukdev.bearingsinfo.characteristic.Characteristic;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.bearingsinfo.util.DataFileUtil;
import com.pazukdev.bearingsinfo.util.SpecificStringUtil;
import com.pazukdev.bearingsinfo.util.WeightUtil;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class MotorcycleDtoFactory extends AbstractDtoFactory<MotorcycleDto> {

    @Override
    protected DataFileContent obtainContent() {
        return parse(DataFileUtil.motorcycleDataFilePath());
    }

    @Override
    protected MotorcycleDto createDto() {
        return new MotorcycleDto();
    }

    @Override
    protected void applyCharacteristics(final @NotNull MotorcycleDto dto,
                                        final @NotNull Map<Characteristic, String> characteristics) {

        applyName(dto, characteristics);
        applyManufacturer(dto, characteristics);
        applyWeight(dto, characteristics);
    }

    private void applyManufacturer(@NotNull final MotorcycleDto dto,
                                   @NotNull final Map<Characteristic, String> characteristics) {
        final String data= characteristics.get(Characteristic.MANUFACTURER);

        if (SpecificStringUtil.isEmpty(data)) return;
        dto.setManufacturer(data);
    }

    private void applyWeight(@NotNull final MotorcycleDto dto,
                             @NotNull final Map<Characteristic, String> characteristics) {
        final String data= characteristics.get(Characteristic.WEIGHT_KG);

        if (data == null) return;
        final Integer weight_kg = Integer.parseInt(data);
        dto.setWeightG(WeightUtil.toG(weight_kg));
    }

}
