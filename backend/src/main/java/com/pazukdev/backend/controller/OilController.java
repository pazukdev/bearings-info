package com.pazukdev.backend.controller;

import com.pazukdev.backend.converter.OilConverter;
import com.pazukdev.backend.dto.product.OilDto;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.service.OilService;
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
@RequestMapping("/oil")
@Api(tags = "Oil Controller", value = "API methods for oils")
@RequiredArgsConstructor
public class OilController {

    private final OilService service;
    private final OilConverter converter;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all oils")
    public List<OilDto> getAll() {
        return converter.convertToDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Get oil")
    public OilDto get(@PathVariable("id") Long id) throws ProductNotFoundException {
        return converter.convertToDto(service.getOne(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new oil")
    public boolean create(@RequestBody final OilDto dto) throws EntityExistsException, JSONException {
        return service.create(dto) != null;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete oil")
    public OilDto delete(@PathVariable("id") final Long id) throws ProductNotFoundException {
        return converter.convertToDto(service.delete(id));
    }

    @PostMapping("/delete-all")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete oils")
    public List<OilDto> deleteAll(@RequestBody final Long[] ids) throws ProductNotFoundException {
        return converter.convertToDtoList(service.deleteAll(Arrays.asList(ids)));
    }

}
