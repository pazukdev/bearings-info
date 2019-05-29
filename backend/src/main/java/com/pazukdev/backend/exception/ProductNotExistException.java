package com.pazukdev.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductNotExistException extends Exception {

    private HttpStatus status;
    private String message;

    public ProductNotExistException(final Long id) {
        status = HttpStatus.NOT_FOUND;
        message = "product under id == " + id + " is not exist";
    }

}
