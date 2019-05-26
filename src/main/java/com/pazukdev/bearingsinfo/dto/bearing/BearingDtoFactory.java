package com.pazukdev.bearingsinfo.dto.bearing;

import com.pazukdev.bearingsinfo.DataFileContent;
import com.pazukdev.bearingsinfo.characteristic.Characteristic;
import com.pazukdev.bearingsinfo.dto.abstraction.AbstractDtoFactory;
import com.pazukdev.bearingsinfo.util.DataFileUtil;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pazukdev.bearingsinfo.util.SpecificStringUtil.getIntegerBetweenParentheses;
import static com.pazukdev.bearingsinfo.util.SpecificStringUtil.getStringBeforeParentheses;
import static com.pazukdev.bearingsinfo.util.SpecificStringUtil.isEmpty;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class BearingDtoFactory extends AbstractDtoFactory<BearingDto> {

    @Override
    protected DataFileContent obtainContent() {
        return parse(DataFileUtil.bearingDataFilePath());
    }

    @Override
    protected BearingDto createDto() {
        return new BearingDto();
    }

    @Override
    protected void applyCharacteristics(final @NotNull BearingDto dto,
                                        final @NotNull Map<Characteristic, String> characteristics) {
        applyName(dto, characteristics);
        applyType(dto, characteristics);
        applyRollingElement(dto, characteristics);
        applyRollingElementsQuantity(dto, characteristics);
    }

    private void applyType(@NotNull final BearingDto dto,
                             @NotNull final Map<Characteristic, String> characteristics) {
        final String data= characteristics.get(Characteristic.TYPE);

        if (isEmpty(data)) return;
        dto.setType(data);
    }

    private void applyRollingElement(@NotNull final BearingDto bearingDto,
                                     @NotNull final Map<Characteristic, String> characteristics) {
        final String data= characteristics.get(Characteristic.ROLLING_ELEMENT);
        final String rollingElementData = getStringBeforeParentheses(data);

        if (isEmpty(rollingElementData)) return;
        bearingDto.setRollingElement(rollingElementData);
    }

    private void applyRollingElementsQuantity(@NotNull final BearingDto dto,
                                              @NotNull final Map<Characteristic, String> characteristics) {
        final String data= characteristics.get(Characteristic.ROLLING_ELEMENT);
        final Integer rollingElementsQuantity = getIntegerBetweenParentheses(data);

        if (rollingElementsQuantity == null) return;
        dto.setRollingElementsQuantity(rollingElementsQuantity);
    }
}
















