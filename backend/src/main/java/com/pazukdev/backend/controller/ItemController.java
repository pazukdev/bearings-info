package com.pazukdev.backend.controller;

import com.pazukdev.backend.dto.ItemView;
import com.pazukdev.backend.dto.Message;
import com.pazukdev.backend.service.ItemService;
import com.pazukdev.backend.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author Siarhei Sviarkaltsau
 */
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@Api(tags = "Item Controller", value = "API methods for items")
@CrossOrigin
public class ItemController {

    private final ItemService service;

    @GetMapping("/get-view/{id}/{userName}/{language}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get item")
    public ItemView get(@PathVariable final Long id,
                        @PathVariable final String userName,
                        @PathVariable final String language)  {
        return service.createItemView(id, userName, language);
    }

    @PostMapping("/create-view/{category}/{name}/{userName}/{language}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new item")
    public ItemView create(@PathVariable final String category,
                           @PathVariable final String name,
                           @PathVariable final String userName,
                           @PathVariable final String language) {
        return service.createNewItemView(category, name, userName, language);
    }

    @PutMapping("/update-view/{id}/{userName}/{language}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update item")
    public ItemView update(@PathVariable final Long id,
                           @PathVariable String userName,
                           @PathVariable String language,
                           @RequestBody final ItemView itemView) {
        return service.updateItemView(id, userName, language, itemView);
    }

    @RequestMapping(value = "/translation-download", method = RequestMethod.GET)
    public void getFile(final HttpServletResponse response) throws IOException {
        final InputStream inputStream = Files.newInputStream(FileUtil.getDictionaryFilePath());
        IOUtils.copy(inputStream, response.getOutputStream());
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment;filename=dictionary.txt");
        response.flushBuffer();
    }

    @RequestMapping(value = "/translation-upload", method = RequestMethod.POST)
    public void getFile(@RequestBody final Message message) throws IOException {
        FileUtil.createDictionaryFileInFileSystem(message.getText().getBytes(StandardCharsets.UTF_8));
    }

}
