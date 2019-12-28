package com.pazukdev.backend.validator;

import com.pazukdev.backend.dto.user.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
public class CredentialsValidator {

    public List<String> validate(final UserDto dto, final boolean nameExists, final boolean emailExists) {
        final List<String> validationMessages = new ArrayList<>();

        if (!EmailValidator.getInstance().isValid(dto.getEmail())) {
            validationMessages.add("Invalid email");
        }
        if (StringUtils.isBlank(dto.getName())) {
            validationMessages.add("Login is empty");
        }
        if (StringUtils.isBlank(dto.getPassword())) {
            validationMessages.add("Password is empty");
        }
        if (!dto.getRepeatedPassword().equals(dto.getPassword())) {
            validationMessages.add("Passwords are different");
        }
        if (nameExists) {
            validationMessages.add(createUserExistsMessage("name"));
        }
        if (emailExists) {
            validationMessages.add(createUserExistsMessage("email"));
        }

        return validationMessages;
    }

    private String createUserExistsMessage(final String userParameter) {
        return "User with this " + userParameter + " already exists";
    }

}
