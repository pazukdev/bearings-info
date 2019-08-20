package com.pazukdev.backend.controller;

import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.dto.UserDto;
import com.pazukdev.backend.dto.item.ItemDto;
import com.pazukdev.backend.dto.table.TableDto;
import com.pazukdev.backend.service.UserService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Set;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "User Controller", value = "API methods for Users")
public class UserController {

    private final UserService service;
    private final UserConverter userConverter;

    @GetMapping("/admin/user/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all Users. Admins-only permitted")
    public List<UserDto> getAll() {
        return userConverter.convertToDtoList(service.findAll());
    }

    @GetMapping("/admin/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get User. Admins-only permitted")
    public UserDto get(@PathVariable("id") Long id) {
        return userConverter.convertToDto(service.getOne(id));
    }

    @PostMapping("/user/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new User")
    public List<String> create(@RequestBody final UserDto dto) throws EntityExistsException, JSONException {
        return service.createUser(dto);
    }

    @PutMapping("/admin/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update user")
    public List<String> update(@PathVariable("id") final Long id, @RequestBody final UserDto dto) {
        return service.updateUser(id, dto);
    }

    @DeleteMapping("/admin/user/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete User. Admins-only permitted")
    public UserDto delete(@PathVariable("id") final Long id) {
        return userConverter.convertToDto(service.delete(id));
    }

    @DeleteMapping("/admin/user/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete all users by ids list")
    public List<UserDto> delete(@RequestBody final List<Long> ids) {
        return userConverter.convertToDtoList(service.deleteAll(ids));
    }

    @GetMapping("/admin/user/roles")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get user roles")
    public Set<String> getRoles() {
        return service.getRoles();
    }

    @PutMapping(value = "{userName}/add-item")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Add item to wish list")
    public Boolean addItem(@PathVariable final String userName, @RequestBody final ItemDto dto) {
        return service.addItem(userName, dto);
    }

    @PutMapping(value = "{userName}/remove-item/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Remove item from wish list")
    public Boolean removeItem(@PathVariable final String userName, @PathVariable final Long itemId) {
        return service.removeItem(userName, itemId);
    }

    @GetMapping(value = "{userName}/wishlist")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get user wishlist")
    public TableDto wishList(@PathVariable final String userName) {
        return service.getItemTable(userName);
    }

}
