package com.pazukdev.backend.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class MotorcycleSpeedReportRS implements Serializable {

    private final static long serialVersionUID = 12343L;

    private Integer maxSpeedKmh;

}
