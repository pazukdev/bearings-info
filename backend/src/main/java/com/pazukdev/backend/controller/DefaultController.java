package com.pazukdev.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@Api(tags = "Default Controller", value = "Auxiliary API methods")
@RequiredArgsConstructor
public class DefaultController {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get test response from server")
    public String getTestResponse() {
        return "This is test response";
    }

}