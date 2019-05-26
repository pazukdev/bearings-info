package com.pazukdev.bearingsinfo.controller;

import com.pazukdev.bearingsinfo.dto.seal.SealDto;
import com.pazukdev.bearingsinfo.service.SealService;
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
@RequestMapping("/seal")
@RequiredArgsConstructor
public class SealController {

    private final SealService service;

    @PostMapping("/create")
    public String createSeal(@RequestBody final SealDto dto) {
        service.createProduct(dto);
        return "Seal created";
    }

    @GetMapping("/list")
    public List<SealDto> getAllSeals() {
        return service.getProductsList();
    }

}
