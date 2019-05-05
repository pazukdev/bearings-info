package com.pazukdev.bearingsinfo.dto;

import lombok.Data;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class BearingDto {

    private Long id;
    private String name;
    private String majorLocation;
    private Integer quantity;

}
