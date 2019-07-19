package com.pazukdev.backend.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@Api(tags = "Default Controller", value = "Default API methods")
public class DefaultController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/login";
    }

}
