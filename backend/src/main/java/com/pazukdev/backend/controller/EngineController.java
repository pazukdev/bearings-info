package com.pazukdev.backend.controller;

import com.pazukdev.backend.dto.product.unit.engine.EngineDto;
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
@Api(tags = "Engine Controller", value = "API methods for Engines")
@RequiredArgsConstructor
public class EngineController {

    private final EngineService service;

    @GetMapping("/list")
    @ApiOperation(value = "Get info about all Engines")
    public List<EngineDto> getAll() {
        return service.getProductsList();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get info about Engine")
    public EngineDto get(@PathVariable("id") Long id) throws ProductNotFoundException {
        return service.get(id);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a new Engine")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean create(@RequestBody final EngineDto dto) throws EntityExistsException, JSONException {
        service.create(dto);
        return true;
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete Engine by id")
    @ResponseStatus(HttpStatus.OK)
    public EngineDto delete(@PathVariable("id") final Long id) throws ProductNotFoundException {
        return service.delete(id);
    }

    @PostMapping("/delete-all")
    @ApiOperation(value = "Delete Engines by ids")
    @ResponseStatus(HttpStatus.OK)
    public List<EngineDto> deleteAll(@RequestBody final Long[] ids) throws ProductNotFoundException {
        return service.deleteAll(Arrays.asList(ids));
    }
    
}
