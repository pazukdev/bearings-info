package com.pazukdev.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityExistsException;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductNotFoundException extends EntityExistsException {

    private HttpStatus status;
    private String message;

    public ProductNotFoundException(final Long id) {
        status = HttpStatus.NOT_FOUND;
        message = "product under id == " + id + " is not exist";
    }

}
