package com.pazukdev.backend.controller;

import com.pazukdev.backend.converter.SealConverter;
import com.pazukdev.backend.dto.product.SealDto;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.service.SealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequestMapping("/seal")
@Api(tags = "Seal Controller", value = "API methods for seals")
@RequiredArgsConstructor
public class SealController {

    private final SealService service;
    private final SealConverter converter;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all seals")
    public List<SealDto> getAll() {
        return converter.convertToDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get seal")
    public SealDto get(@PathVariable("id") Long id) throws ProductNotFoundException {
        return converter.convertToDto(service.getOne(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new seal")
    public boolean create(@RequestBody final SealDto dto) throws EntityExistsException, JSONException {
        return service.create(dto) != null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete seal")
    public SealDto delete(@PathVariable("id") final Long id) throws ProductNotFoundException {
        return converter.convertToDto(service.delete(id));
    }

    @GetMapping("/rotations")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get seal rotations")
    public Set<String> getRotations() {
        return service.getRotations();
    }

    @GetMapping("/materials")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get seal materials")
    public Set<String> getMaterials() {
        return service.getMaterials();
    }

}
