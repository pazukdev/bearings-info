package com.pazukdev.backend.controller;

import com.pazukdev.backend.converter.MotorcycleConverter;
import com.pazukdev.backend.dto.product.MotorcycleDto;
import com.pazukdev.backend.dto.report.FuelReportRS;
import com.pazukdev.backend.dto.report.SpeedReportRS;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.service.MotorcycleService;
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
@RequestMapping("/motorcycle")
@Api(tags = "Motorcycle Controller", value = "API methods for motorcycles")
@RequiredArgsConstructor
public class MotorcycleController {

    private final MotorcycleService service;
    private final MotorcycleConverter converter;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all motorcycles")
    public List<MotorcycleDto> getAll() {
        return converter.convertToDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Get motorcycle")
    public MotorcycleDto get(@PathVariable("id") Long id) throws ProductNotFoundException {
        return converter.convertToDto(service.getOne(id));
    }

    @GetMapping("/{id}/speed-report")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get motorcycle speed report")
    public SpeedReportRS getSpeedReport(@PathVariable("id") Long id) throws ProductNotFoundException {
        return service.getSpeedReport(id);
    }

    @GetMapping("/{id}/fuel-report")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get motorcycle fuel report")
    public FuelReportRS getFuelReport(@PathVariable("id") Long id) throws ProductNotFoundException {
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
        return converter.convertToDto(service.delete(id));
    }

    @PostMapping("/delete-all")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete motorcycles")
    public List<MotorcycleDto> deleteAll(@RequestBody final Long[] ids) throws ProductNotFoundException {
        return converter.convertToDtoList(service.deleteAll(Arrays.asList(ids)));
    }

}
