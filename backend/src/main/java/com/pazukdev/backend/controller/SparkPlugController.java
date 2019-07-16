package com.pazukdev.backend.controller;

import com.pazukdev.backend.dto.product.sparkplug.SparkPlugDto;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.service.SparkPlugService;
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
@RequestMapping("/spark-plug")
@Api(tags = "Spark Plug Controller", value = "API methods for spark plugs")
@RequiredArgsConstructor
public class SparkPlugController {

    private final SparkPlugService service;

    @GetMapping("/list")
    @ApiOperation(value = "Get info about all spark plugs")
    public List<SparkPlugDto> getAll() {
        return service.getProductsList();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get info about spark plug")
    public SparkPlugDto get(@PathVariable("id") Long id) throws ProductNotFoundException {
        return service.get(id);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a new spark plug")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean create(@RequestBody final SparkPlugDto dto) throws EntityExistsException, JSONException {
        service.create(dto);
        return true;
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete spark plug by id")
    @ResponseStatus(HttpStatus.OK)
    public SparkPlugDto delete(@PathVariable("id") final Long id) throws ProductNotFoundException {
        return service.delete(id);
    }

    @PostMapping("/delete-all")
    @ApiOperation(value = "Delete spark plugs by ids")
    @ResponseStatus(HttpStatus.OK)
    public List<SparkPlugDto> deleteAll(@RequestBody final Long[] ids) throws ProductNotFoundException {
        return service.deleteAll(Arrays.asList(ids));
    }

}
