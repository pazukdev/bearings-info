package com.pazukdev.backend.search;

import com.pazukdev.backend.dto.abstraction.AbstractDtoFactory;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class DefaultSearchRequest implements Serializable {

    private final static long serialVersionUID = 12343L;

    private int offset;
    private int limit = Integer.MAX_VALUE;
    private String name;

}
