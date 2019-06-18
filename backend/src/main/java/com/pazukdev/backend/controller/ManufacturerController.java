package com.pazukdev.backend.controller;

import com.pazukdev.backend.dto.manufacturer.ManufacturerDto;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.search.DefaultSearchRequest;
import com.pazukdev.backend.service.ManufacturerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/list")
    @ApiOperation(value = "get info about all manufacturers")
    public List<ManufacturerDto> getAll() {
        return service.getProductsList();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get info about manufacturer")
    public ManufacturerDto get(@PathVariable("id") final Long id) throws ProductNotFoundException {
        return service.get(id);
    }

    @PostMapping("/create")
    @ApiOperation(value = "create a new manufacturer")
    public String create(@RequestBody final ManufacturerDto dto) throws EntityExistsException, JSONException {
        return new JSONObject().put("id", service.create(dto).getId()).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete manufacturer")
    public void delete(@PathVariable("id") final Long id) throws ProductNotFoundException {
        service.delete(id);
    }

    @PostMapping(value = "/search")
    @ApiOperation(value = "search for manufacturer")
    public ManufacturerDto searchManufacturer(@RequestBody final DefaultSearchRequest request) {
        return service.search(request);
    }

}







