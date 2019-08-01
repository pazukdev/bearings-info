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
@Api(tags = "Test Controller", value = "Test methods")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/test/protected")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get test response from protected url")
    public String getTestResponseFromProtectedUrl() {
        return "This is test response from protected url";
    }

    @GetMapping("/test/public")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get test response from public url")
    public String getTestResponseFromPublicUrl() {
        return "This is test response from public url";
    }

}
