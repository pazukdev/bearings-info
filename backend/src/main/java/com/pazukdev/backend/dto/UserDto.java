package com.pazukdev.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Siarhei Sviarkaltsau
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserDto extends AbstractDto {

    private final static long serialVersionUID = 12343L;

    private Integer rating;
    private String password;
    private String repeatedPassword;
    @ApiModelProperty(hidden = true)
    private String role;
    private Long wishListId;

}
