package com.pazukdev.backend.controller;

import com.pazukdev.backend.dto.item.ItemView;
import com.pazukdev.backend.exception.ProductNotFoundException;
import com.pazukdev.backend.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Api(tags = "Item Controller", value = "API methods for items")
public class ItemController {

    public static final String uploadingDir = "/static/";

    @Autowired
    private HttpServletRequest request;

    private final ItemService service;

    @PutMapping("/file-upload/{fileName}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Upload file")
    public boolean uploadFile(@PathVariable final String fileName,
                              @RequestBody MultipartFile uploadedFile) throws IOException {
        String realPathtoUploads =  request.getServletContext().getRealPath(uploadingDir);
        if(! new File(realPathtoUploads).exists())
        {
            new File(realPathtoUploads).mkdir();
        }


        String orgName = uploadedFile.getOriginalFilename();
        String filePath = realPathtoUploads + orgName;
        File dest = new File(filePath);
        uploadedFile.transferTo(dest);
        return true;
    }

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
