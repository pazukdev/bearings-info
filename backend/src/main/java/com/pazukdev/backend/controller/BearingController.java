package com.pazukdev.backend.controller;

import com.pazukdev.backend.converter.BearingConverter;
import com.pazukdev.backend.dto.product.BearingDto;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.service.BearingService;
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
@RequestMapping("/bearing")
@RequiredArgsConstructor
@Api(tags = "Bearing Controller", value = "API methods for bearings")
public class BearingController {

    private final BearingService service;
    private final BearingConverter converter;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all bearings")
    public List<BearingDto> getAll() {
        return converter.convertToDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Get bearing")
    public BearingDto get(@PathVariable("id") Long id) throws ProductNotFoundException {
        return converter.convertToDto(service.getOne(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new bearing")
    public boolean create(@RequestBody final BearingDto dto) throws EntityExistsException, JSONException {
        return service.create(dto) != null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete bearing")
    public void delete(@PathVariable("id") final Long id) throws ProductNotFoundException {
        service.delete(id);
    }

    @PostMapping(value = "/search-by-name")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Get bearing by name")
    public BearingDto searchByName(@RequestBody final String name) {
        return converter.convertToDto(service.findByName(name));
    }

    @PostMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get bearings list by ids list")
    public List<BearingDto> search(@RequestBody final List<Long> ids) {
        return converter.convertToDtoList(service.search(ids));
    }

}










