package com.pazukdev.backend.controller;

import com.pazukdev.backend.dto.Message;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.repository.UserActionRepository;
import com.pazukdev.backend.service.UserService;
import com.pazukdev.backend.util.FileUtil;
import com.pazukdev.backend.util.UserActionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
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
@RequestMapping("/file")
@RequiredArgsConstructor
@Api(tags = "File Controller", value = "API methods for files upload / download")
@CrossOrigin
public class FileController {

    private final UserService userService;
    private final UserActionRepository userActionRepository;

    @GetMapping(value = "/dictionary/download")
    @ApiOperation(value = "Download dictionary file which contains translations for app texts")
    public void downloadDictionaryFile(final HttpServletResponse response) throws IOException {
        final InputStream inputStream = Files.newInputStream(FileUtil.getDictionaryFilePath());
        IOUtils.copy(inputStream, response.getOutputStream());
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment;filename=dictionary.txt");
        response.flushBuffer();
    }

    @PutMapping(value = "/dictionary/upload/{username}")
    @ApiOperation(value = "Upload new translations for the app")
    public String uploadDictionaryFile(@PathVariable final String username,
                                     @RequestBody final Message message) throws IOException {

        final int dictionarySize = FileUtil.getTxtFileLines(FileUtil.getDictionaryFilePath()).size();
        final int newDictionarySize = message.getText().split(System.getProperty("line.separator")).length;
        final int difference = dictionarySize - newDictionarySize;
        final int removedLinesLimit = 2;

        if (difference > removedLinesLimit) {
            return "New dictionary not accepted. " +
                    "New size is " + newDictionarySize + " lines. "
                    + difference + " lines were removed. " +
                    "You can't remove more than " + removedLinesLimit + " lines at a time";
        }

        FileUtil.createDictionaryFileInFileSystem(message.getText().getBytes(StandardCharsets.UTF_8));

        final String plus = -difference > 0 ? "+" : "";
        String changed = ": " + plus + (-difference) + " lines";

        final UserEntity user = userService.findByName(username);
        UserActionUtil.processUploadDictionaryAction("upload dictionary", changed, user, userActionRepository);

        if (difference == 0) {
            changed = "";
        }
        return "New dictionary accepted" + changed;
    }

}
