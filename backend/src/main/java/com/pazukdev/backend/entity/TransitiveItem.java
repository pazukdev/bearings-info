package com.pazukdev.backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TransitiveItem extends AbstractEntity {

    private String category;
    private String image;
    private String description = "-";
    private String replacer = "-";
    private String wiki;
    private String website;
    private String manual;
    private String parts;
    private String drawings;
    private String websiteLang;

}
