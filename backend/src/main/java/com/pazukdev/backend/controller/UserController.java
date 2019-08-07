package com.pazukdev.backend.controller;

import com.pazukdev.backend.converter.UserConverter;
import com.pazukdev.backend.dto.UserDto;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "User Controller", value = "API methods for Users")
public class UserController {

    private final UserService service;
    private final UserConverter converter;

    @GetMapping("/admin/user/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all Users. Admins-only permitted")
    public List<UserDto> getAll() {
        return converter.convertToDtoList(service.findAll());
    }

    @GetMapping("/admin/user/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Get User. Admins-only permitted")
    public UserDto get(@PathVariable("id") Long id) {
        return converter.convertToDto(service.getOne(id));
    }

    @PostMapping("/user/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new User")
    public List<String> create(@RequestBody final UserDto dto) throws EntityExistsException, JSONException {
        return service.createUser(dto);
    }

    @PostMapping("/admin/user/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new Admin. Admins-only permitted")
    public List<String> createAdmin(@RequestBody final UserDto dto) throws EntityExistsException, JSONException {
        return service.createAdmin(dto);
    }

    @DeleteMapping("/admin/user/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete User. Admins-only permitted")
    public UserDto delete(@PathVariable("id") final Long id) {
        return converter.convertToDto(service.delete(id));
    }

}
