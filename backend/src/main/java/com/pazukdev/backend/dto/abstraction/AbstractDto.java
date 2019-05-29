package com.pazukdev.backend.dto.abstraction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class AbstractDto implements Serializable {

    private final static long serialVersionUID = 12343L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String name;

}
