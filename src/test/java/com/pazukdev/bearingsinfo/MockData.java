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
        bearing.setType("bearing type");
        bearing.setRollingElement("bearing rolling element");
        bearing.setRollingElementsQuantity(1);
        return bearing;
    }

    public static BearingDto bearingDto() {
        final BearingDto bearingDto = new BearingDto();
        bearingDto.setName("bearingDto name");
        bearingDto.setType("bearingDto type");
        bearingDto.setRollingElement("bearingDto rolling element");
        bearingDto.setRollingElementsQuantity(2);
        return bearingDto;
    }

}
