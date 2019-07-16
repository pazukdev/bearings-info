package com.pazukdev.backend.controller;

import com.pazukdev.backend.dto.product.oil.OilDto;
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
@Api(tags = "Oil Controller", value = "API methods for Oils")
@RequiredArgsConstructor
public class OilController {

    private final OilService service;

    @GetMapping("/list")
    @ApiOperation(value = "Get info about all Oils")
    public List<OilDto> getAll() {
        return service.getProductsList();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get info about Oil")
    public OilDto get(@PathVariable("id") Long id) throws ProductNotFoundException {
        return service.get(id);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a new Oil")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean create(@RequestBody final OilDto dto) throws EntityExistsException, JSONException {
        service.create(dto);
        return true;
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete Oil by id")
    @ResponseStatus(HttpStatus.OK)
    public OilDto delete(@PathVariable("id") final Long id) throws ProductNotFoundException {
        return service.delete(id);
    }

    @PostMapping("/delete-all")
    @ApiOperation(value = "Delete Oils by ids")
    @ResponseStatus(HttpStatus.OK)
    public List<OilDto> deleteAll(@RequestBody final Long[] ids) throws ProductNotFoundException {
        return service.deleteAll(Arrays.asList(ids));
    }

}
