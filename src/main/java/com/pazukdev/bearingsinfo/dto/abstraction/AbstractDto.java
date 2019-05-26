package com.pazukdev.bearingsinfo.dto.abstraction;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class AbstractDto implements Serializable {

    private final static long serialVersionUID = 12343L;

    private Long id;
    private String name;

}
