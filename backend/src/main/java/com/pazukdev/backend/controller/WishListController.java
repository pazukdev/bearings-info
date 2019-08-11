package com.pazukdev.backend.controller;

import com.pazukdev.backend.converter.BearingConverter;
import com.pazukdev.backend.converter.WishListConverter;
import com.pazukdev.backend.dto.WishListDto;
import com.pazukdev.backend.dto.product.BearingDto;
import com.pazukdev.backend.service.WishListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/wish-list")
@RequiredArgsConstructor
@Api(tags = "Wish List Controller", value = "API methods for wish lists")
public class WishListController {

    private final WishListService service;
    private final WishListConverter wishListConverter;
    private final BearingConverter bearingConverter;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all wish lists")
    public List<WishListDto> getAll() {
        return wishListConverter.convertToDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get wish list")
    public WishListDto get(@PathVariable("id") Long id) throws Exception {
        return wishListConverter.convertToDto(service.getOne(id));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new wish list")
    public boolean create(@RequestBody final WishListDto dto) throws EntityExistsException, JSONException {
        service.create(dto);
        return true;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete wish list")
    public void delete(@PathVariable("id") final Long id) throws Exception {
        service.delete(id);
    }

    @PostMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get wish lists list by ids list")
    public List<WishListDto> search(@RequestBody final List<Long> ids) {
        return wishListConverter.convertToDtoList(service.search(ids));
    }

    @PutMapping(value = "/add-item")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Add item to wish list")
    public Boolean addItem(final Long wishListId, final BearingDto dto) {
        return service.addItem(wishListId, bearingConverter.convertToEntity(dto));
    }

    @PutMapping(value = "/remove-item")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Remove item from wish list")
    public Boolean removeItem(final Long wishListId, final Long bearingToRemoveId) {
        return service.removeItem(wishListId, bearingToRemoveId);
    }

}
