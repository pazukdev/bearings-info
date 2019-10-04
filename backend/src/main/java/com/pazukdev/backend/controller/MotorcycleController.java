package com.pazukdev.backend.controller;

import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.dto.product.MotorcycleDto;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.dto.table.TableViewDto;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.service.MotorcycleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequestMapping("/motorcycle")
@Api(tags = "Motorcycle Controller", value = "API methods for motorcycles")
@RequiredArgsConstructor
public class MotorcycleController {

    private final MotorcycleService service;
    private final MotorcycleConverter motorcycleConverter;

//    @GetMapping("/list")
//    @ResponseStatus(HttpStatus.OK)
//    @ApiOperation(value = "Get all motorcycles")
//    public List<MotorcycleDto> getAll() {
//        return motorcycleConverter.convertToDtoList(service.findAll());
//    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get motorcycle")
    public MotorcycleDto get(@PathVariable("id") Long id) throws ProductNotFoundException {
        return motorcycleConverter.convertToDto(service.getOne(id));
    }

    @GetMapping("/{id}/speed-report")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get motorcycle speed report")
    public TableDto getSpeedReport(@PathVariable("id") Long id) {
        return service.getSpeedReport(id);
    }

    @GetMapping("/{id}/fuel-report")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get motorcycle fuel report")
    public TableDto getFuelReport(@PathVariable("id") Long id) {
        return service.getFuelReport(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new motorcycle")
    public boolean create(@RequestBody final MotorcycleDto dto) throws EntityExistsException, JSONException {
        return service.create(dto) != null;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete motorcycle")
    public MotorcycleDto delete(@PathVariable("id") final Long id) throws ProductNotFoundException {
        return motorcycleConverter.convertToDto(service.delete(id));
    }

    @GetMapping(value = "{motorcycleName}/items")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get motorcycle items")
    public TableViewDto getCategorizedWishList(@PathVariable final String motorcycleName) {
        return service.createTableView(motorcycleName);
    }

}
