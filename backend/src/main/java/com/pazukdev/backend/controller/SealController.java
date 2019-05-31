package com.pazukdev.backend.controller;

import com.pazukdev.backend.dto.seal.SealDto;
import com.pazukdev.backend.entity.Seal;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.service.SealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityExistsException;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequestMapping("/seal")
@Api(tags = "Seal Controller", value = "API methods for seals")
@RequiredArgsConstructor
public class SealController {

    private final SealService service;

    @GetMapping("/list")
    @ApiOperation(value = "Get info about all seals")
    public List<SealDto> getAll() {
        return service.getProductsList();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get info about seal")
    public SealDto get(@PathVariable("id") Long id) throws ProductNotFoundException {
        return service.get(id);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a new seal")
    public String create(@RequestBody final SealDto dto) throws EntityExistsException, JSONException {
        return new JSONObject().put("id", service.create(dto).getId()).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete seal")
    public Response delete(@PathVariable("id") Long id) {
        try {
            final Seal seal = service.delete(id);
            return Response.ok().entity(seal.getId().toString()).build();
        } catch (ProductNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
