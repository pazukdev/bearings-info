package com.pazukdev.backend.dto.report;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class SpeedReportRS implements Serializable {

    private final static long serialVersionUID = 12343L;

    private Integer maxSpeedKmh;

}
