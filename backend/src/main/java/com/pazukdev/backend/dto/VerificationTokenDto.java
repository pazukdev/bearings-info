package com.pazukdev.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VerificationTokenDto extends AbstractDto {

    private String token;
    private LocalDate expiryDate;

}
