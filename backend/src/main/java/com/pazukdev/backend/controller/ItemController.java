package com.pazukdev.backend.controller;

import com.pazukdev.backend.dto.item.ItemView;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Api(tags = "Item Controller", value = "API methods for items")
public class ItemController {

    private final ItemService service;

    @GetMapping("/get-view/{id}/{userName}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get item")
    public ItemView getView(@PathVariable final Long id, @PathVariable final String userName)  {
        return service.createItemView(id, userName);
    }

    @PostMapping("/create-view/{category}/{name}/{userName}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new item")
    public ItemView createView(@PathVariable final String category,
                           @PathVariable final String name,
                           @PathVariable String userName) {
        return service.createNewItemView(category, name, userName);
    }

    @PutMapping("/update-view/{id}/{userName}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update item")
    public ItemView updateView(@PathVariable final Long id,
                           @PathVariable String userName,
                           @RequestBody final ItemView itemView) throws ProductNotFoundException {
        return service.updateItemView(id, userName, itemView);
    }

}
