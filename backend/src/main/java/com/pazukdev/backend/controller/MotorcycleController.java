package com.pazukdev.backend.controller;

import com.pazukdev.backend.dto.motorcycle.MotorcycleDto;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.service.MotorcycleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequestMapping("/motorcycle")
@Api(tags = "Motorcycle Controller", value = "API methods for motorcycles")
@RequiredArgsConstructor
public class MotorcycleController {

    private final MotorcycleService service;

    @GetMapping("/list")
    @ApiOperation(value = "Get info about all motorcycles")
    public List<MotorcycleDto> getAll() {
        return service.getProductsList();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get info about motorcycle")
    public MotorcycleDto get(@PathVariable("id") Long id) throws ProductNotFoundException {
        return service.get(id);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a new motorcycle")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean create(@RequestBody final MotorcycleDto dto) throws EntityExistsException, JSONException {
        service.create(dto);
        return true;
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete motorcycle by id")
    @ResponseStatus(HttpStatus.OK)
    public MotorcycleDto delete(@PathVariable("id") final Long id) throws ProductNotFoundException {
        return service.delete(id);
    }

    @PostMapping("/delete-all")
    @ApiOperation(value = "Delete motorcycles by ids")
    @ResponseStatus(HttpStatus.OK)
    public List<MotorcycleDto> deleteAll(@RequestBody final Long[] ids) throws ProductNotFoundException {
        return service.deleteAll(Arrays.asList(ids));
    }

}
