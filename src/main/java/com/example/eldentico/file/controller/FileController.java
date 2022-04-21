package com.example.eldentico.file.controller;

import com.example.eldentico.file.service.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public void uploadFile(@RequestBody MultipartFile file) throws IOException {
        log.info("File upload started.");
        fileService.uploadFile(file);
    }

    @PostMapping(value = "/download/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] download(@PathVariable Long id) {
        log.info("File download started.");
        return fileService.downloadFile(id);
    }
}
