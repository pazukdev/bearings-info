package com.pazukdev.bearingsinfo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class BearingDto implements Serializable {

    private final static long serialVersionUID = 12343L;

    private Long id;
    private String name;
    private String unit;
    private Integer quantity;

}
