package com.pazukdev.backend.controller;

import com.pazukdev.backend.converter.ManufacturerConverter;
import com.pazukdev.backend.dto.ManufacturerDto;
import com.pazukdev.backend.dto.search.DefaultSearchRequest;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.service.ManufacturerService;
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

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequestMapping("/manufacturer")
@Api(tags = "Manufacturer Controller", value = "API methods for manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {

    private final ManufacturerService service;
    private final ManufacturerConverter converter;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all manufacturers")
    public List<ManufacturerDto> getAll() {
        return converter.convertToDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Get manufacturer")
    public ManufacturerDto get(@PathVariable("id") final Long id) throws ProductNotFoundException {
        return converter.convertToDto(service.getOne(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new manufacturer")
    public boolean create(@RequestBody final ManufacturerDto dto) throws EntityExistsException, JSONException {
        return service.create(dto) != null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete manufacturer")
    public ManufacturerDto delete(@PathVariable("id") final Long id) throws ProductNotFoundException {
        return converter.convertToDto(service.delete(id));
    }

    @PostMapping(value = "/search")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Get manufacturer by name")
    public ManufacturerDto searchByName(@RequestBody final DefaultSearchRequest request) {
        return converter.convertToDto(service.search(request));
    }

}








