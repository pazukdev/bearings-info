package com.pazukdev.bearingsinfo.controller;

import com.pazukdev.bearingsinfo.dto.BearingDto;
import com.pazukdev.bearingsinfo.service.BearingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequestMapping("/bearing")
public class BearingController {

    private final BearingService service;

    @Autowired
    public BearingController(final BearingService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public String createPerson(@RequestBody final BearingDto bearingDto) {
        service.createBearing(bearingDto);
        return "Bearing created";
    }

    @GetMapping("/list")
    public List<BearingDto> getAllPersons() {
        return service.getBearingsList();
    }

}