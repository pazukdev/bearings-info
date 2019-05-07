package com.pazukdev.bearingsinfo;

import com.pazukdev.bearingsinfo.dbo.Bearing;
import com.pazukdev.bearingsinfo.dto.BearingDto;

/**
 * @author Siarhei Sviarkaltsau
 */
public class MockData {

    public static Bearing bearing() {
        final Bearing bearing = new Bearing();
        bearing.setName("bearing name");
        bearing.setUnit("bearing unit");
        bearing.setQuantity(1);
        return bearing;
    }

    public static BearingDto bearingDto() {
        final BearingDto bearingDto = new BearingDto();
        bearingDto.setName("bearingDto name");
        bearingDto.setUnit("bearingDto unit");
        bearingDto.setQuantity(2);
        return bearingDto;
    }

}
