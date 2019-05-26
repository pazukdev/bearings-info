package com.pazukdev.bearingsinfo.controller;

import com.pazukdev.bearingsinfo.dto.motorcycle.MotorcycleDto;
import com.pazukdev.bearingsinfo.service.MotorcycleService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/motorcycle")
@RequiredArgsConstructor
public class MotorcycleController {

    private final MotorcycleService service;

    @PostMapping("/create")
    public String createMotorcycle(@RequestBody final MotorcycleDto dto) {
        service.createProduct(dto);
        return "Motorcycle created";
    }

    @GetMapping("/list")
    public List<MotorcycleDto> getAllMotorcycles() {
        return service.getProductsList();
    }

}
