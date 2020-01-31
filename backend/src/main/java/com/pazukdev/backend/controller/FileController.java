package com.pazukdev.backend.controller;

import com.pazukdev.backend.constant.security.Role;
import com.pazukdev.backend.dto.Message;
import com.pazukdev.backend.entity.UserEntity;
import com.pazukdev.backend.repository.UserActionRepository;
import com.pazukdev.backend.service.UserService;
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

import static com.pazukdev.backend.util.FileUtil.*;

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

    @GetMapping(value = "/{fileName}/download")
    @ApiOperation(value = "Download dictionary file which contains translations for app texts")
    public void download(@PathVariable final String fileName,
                         final HttpServletResponse response) throws IOException {


        final InputStream inputStream = Files.newInputStream(getTxtFilePath(fileName));
        IOUtils.copy(inputStream, response.getOutputStream());
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + FileFormat.TXT);
        response.flushBuffer();
    }

    @PutMapping(value = "/{fileName}/upload/{username}")
    @ApiOperation(value = "Upload new translations for the app")
    public String upload(@PathVariable final String fileName,
                         @PathVariable final String username,
                         @RequestBody final Message message) throws IOException {

        if (fileName == null) {
            return "File name is null";
        }

        if (fileName.equals(FileName.DICTIONARY)) {
            return uploadDictionaryFile(username, message);
        }

        createFileInFileSystem(fileName, message.getText().getBytes(StandardCharsets.UTF_8));

        return "New " + fileName + " file accepted";
    }

    private String uploadDictionaryFile(final String username, final Message message) throws IOException {
        final UserEntity user = userService.findByName(username);

        final String text = message.getText();
        final int dictionarySize = getTxtFileLines(getTxtFilePath(FileName.DICTIONARY)).size();
        final int newDictionarySize = text.split(System.getProperty("line.separator")).length;
        final int difference = dictionarySize - newDictionarySize;
        final int removedLinesLimit = user.getRole() == Role.ADMIN ? 20 : 1;

        if (difference > removedLinesLimit) {
            return "New dictionary not accepted. " +
                    "New size is " + newDictionarySize + " lines. "
                    + difference + " lines were removed. " +
                    "You can't remove more than " + removedLinesLimit + " lines at a time";
        }

        createFileInFileSystem(FileName.DICTIONARY, text.getBytes(StandardCharsets.UTF_8));

        final String plus = -difference > 0 ? "+" : "";
        String changed = ": " + plus + (-difference) + " lines";

        UserActionUtil.processUploadDictionaryAction("upload dictionary", changed, user, userActionRepository);

        if (difference == 0) {
            changed = "";
        }
        return "New dictionary accepted" + changed;
    }

}
