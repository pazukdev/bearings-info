package com.pazukdev.backend.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class RateReplacer implements Serializable {

    private final static long serialVersionUID = 12343L;

    private String action;
    private Long itemId;
    private Set<Long> ratedItems;
    private List<NestedItemDto> replacers;

}
