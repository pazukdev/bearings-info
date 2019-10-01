package com.pazukdev.backend.controller;

import com.pazukdev.backend.dto.table.ItemView;
import com.pazukdev.backend.entity.item.Item;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Api(tags = "Item Controller", value = "API methods for items")
public class ItemController {

    private final ItemService service;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all items")
    public List<Item> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get item")
    public ItemView get(@PathVariable("id") Long id)  {
        return service.createItemView(id);
    }

    @PostMapping("/create/{category}/{name}/{userName}/{actionType}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new item")
    public ItemView create(@PathVariable final String category,
                           @PathVariable final String name,
                           @PathVariable String userName,
                           @PathVariable String actionType) {
        return service.createNewItemView(category, name, userName);
    }

    @PutMapping("/{id}/{userName}/{actionType}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update item")
    public ItemView update(@PathVariable final Long id,
                           @PathVariable String userName,
                           @PathVariable String actionType,
                           @RequestBody final ItemView itemView) throws ProductNotFoundException {
        return service.update(id, userName, itemView);
    }

    @GetMapping("/motorcycles")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all motorcycles")
    public ItemView motorcycleCatalogue() {
        return service.motorcycleCatalogue();
    }

}
