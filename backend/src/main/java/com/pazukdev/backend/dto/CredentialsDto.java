package com.pazukdev.backend.dto;

import lombok.Data;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
public class CredentialsDto {

    private String alias;
    private String password;
    private String role;

}
