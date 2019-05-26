package com.pazukdev.bearingsinfo.dto.seal;

import com.pazukdev.bearingsinfo.DataFileContent;
import com.pazukdev.bearingsinfo.characteristic.Characteristic;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.bearingsinfo.util.DataFileUtil;
import com.pazukdev.bearingsinfo.util.SpecificStringUtil;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class SealDtoFactory extends AbstractDtoFactory<SealDto> {

    @Override
    protected DataFileContent obtainContent() {
        return parse(DataFileUtil.sealDataFilePath());
    }

    @Override
    protected SealDto createDto() {
        return new SealDto();
    }

    @Override
    protected void applyCharacteristics(final @NotNull SealDto dto,
                                        final @NotNull Map<Characteristic, String> characteristics) {

        applyName(dto, characteristics);
        applyRotation(dto, characteristics);
        applyMaterial(dto, characteristics);
    }

    private void applyRotation(@NotNull final SealDto dto,
                           @NotNull final Map<Characteristic, String> characteristics) {
        final String data= characteristics.get(Characteristic.ROTATION);

        if (SpecificStringUtil.isEmpty(data)) return;
        dto.setRotation(data);
    }

    private void applyMaterial(@NotNull final SealDto dto,
                               @NotNull final Map<Characteristic, String> characteristics) {
        final String data= characteristics.get(Characteristic.MATERIAL);

        if (SpecificStringUtil.isEmpty(data)) return;
        dto.setMaterial(data);
    }

}
