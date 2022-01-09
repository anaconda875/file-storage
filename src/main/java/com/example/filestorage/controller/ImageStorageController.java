package com.example.filestorage.controller;

import com.example.filestorage.dto.request.FileDownloadRequest;
import com.example.filestorage.dto.response.ImageResponse;
import com.example.filestorage.service.ImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageStorageController {

    private final ImageStorageService imageStorageService;

    @PostMapping
    public void upload(@RequestHeader("User-Id") String userId, @RequestParam("files") MultipartFile[] images) {
        imageStorageService.upload(userId, images);
    }

    @GetMapping
    public List<ImageResponse> list(@RequestHeader("User-Id") String userId) {
        return imageStorageService.list(userId);
    }

    @PostMapping("/download")
    public ResponseEntity<Resource> downloadAll(@RequestHeader("User-Id") String userId, @Valid @RequestBody FileDownloadRequest request) {
        byte[] content = imageStorageService.zipAndDownloadAll(userId, request);
        InputStreamResource inputStreamResource = new InputStreamResource(new BufferedInputStream(new ByteArrayInputStream(content)));

        return ResponseEntity.ok().contentType(MediaType.valueOf("application/zip"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test.zip\"")
                .contentLength(content.length).body(inputStreamResource);
    }

}
