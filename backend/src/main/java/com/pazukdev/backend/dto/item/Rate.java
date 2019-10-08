package com.pazukdev.backend.dto.item;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class Rate implements Serializable {

    private final static long serialVersionUID = 12343L;

    private String action;
    private Long itemId;

}
