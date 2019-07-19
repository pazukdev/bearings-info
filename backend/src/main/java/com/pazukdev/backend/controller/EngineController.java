package com.pazukdev.backend.controller;

import com.pazukdev.backend.converter.EngineConverter;
import com.pazukdev.backend.dto.product.unit.EngineDto;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.service.EngineService;
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
import java.util.Arrays;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequestMapping("/engine")
@Api(tags = "Engine Controller", value = "API methods for engines")
@RequiredArgsConstructor
public class EngineController {

    private final EngineService service;
    private final EngineConverter converter;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all engines")
    public List<EngineDto> getAll() {
        return converter.convertToDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Get engine")
    public EngineDto get(@PathVariable("id") Long id) throws ProductNotFoundException {
        return converter.convertToDto(service.getOne(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new engine")
    public boolean create(@RequestBody final EngineDto dto) throws EntityExistsException, JSONException {
        return service.create(dto) != null;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete engine")
    public EngineDto delete(@PathVariable("id") final Long id) throws ProductNotFoundException {
        return converter.convertToDto(service.delete(id));
    }

    @PostMapping("/delete-all")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete engines")
    public List<EngineDto> deleteAll(@RequestBody final Long[] ids) throws ProductNotFoundException {
        return converter.convertToDtoList(service.deleteAll(Arrays.asList(ids)));
    }
    
}
