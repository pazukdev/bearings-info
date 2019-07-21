package com.pazukdev.backend.controller;

import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "User Controller", value = "API methods for Users")
public class UserController {

    private final UserService service;
    private final UserConverter converter;

}
