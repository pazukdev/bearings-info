package com.pazukdev.backend.controller;

import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Api(tags = "Item Controller", value = "API methods for items")
public class ItemController {

    private final ItemService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new item")
    public boolean create(@RequestBody final ItemDto dto) throws EntityExistsException, JSONException {
        return service.create(dto) != null;
    }

}