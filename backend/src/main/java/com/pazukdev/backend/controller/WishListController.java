package com.pazukdev.backend.controller;

import com.pazukdev.backend.converter.TransitiveItemConverter;
import com.pazukdev.backend.converter.WishListConverter;
import com.pazukdev.backend.dto.WishListDto;
import com.pazukdev.backend.service.WishListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    private final TransitiveItemConverter transitiveItemConverter;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all wish lists")
    public List<WishListDto> getAll() {
        return wishListConverter.convertToDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get wish list")
    public WishListDto get(@PathVariable("id") Long id) {
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
    public void delete(@PathVariable("id") final Long id) {
        service.delete(id);
    }

    @PostMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get wish lists list by ids list")
    public List<WishListDto> search(@RequestBody final List<Long> ids) {
        return wishListConverter.convertToDtoList(service.search(ids));
    }

}
