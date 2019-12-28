package com.pazukdev.backend.dto.user;

import com.pazukdev.backend.dto.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserView extends AbstractDto {

    private String role;
    private String rating;
    private String email;
    private String imgData = "-";

}
