package com.pazukdev.backend.controller;

import com.pazukdev.backend.dto.product.bearing.BearingDto;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.search.DefaultSearchRequest;
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

    @GetMapping("/list")
    @ApiOperation(value = "Get info about all bearings")
    public List<BearingDto> getAll() {
        return service.getProductsList();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get info about bearing")
    public BearingDto get(@PathVariable("id") Long id) throws ProductNotFoundException {
        return service.get(id);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a new bearing")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean create(@RequestBody final BearingDto dto) throws EntityExistsException, JSONException {
        service.create(dto);
        return true;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete bearing")
    public void delete(@PathVariable("id") final Long id) throws ProductNotFoundException {
        service.delete(id);
    }

    @PostMapping(value = "/search-by-name")
    @ApiOperation(value = "get bearing by name")
    public BearingDto searchByName(@RequestBody final DefaultSearchRequest request) {
        return service.search(request);
    }

    @PostMapping(value = "/search")
    @ApiOperation(value = "get bearings list by ids list")
    public List<BearingDto> search(@RequestBody final List<Long> ids) {
        return service.search(ids);
    }

}