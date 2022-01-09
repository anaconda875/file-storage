package com.example.filestorage.service;

import com.example.filestorage.dto.request.FileDownloadRequest;
import com.example.filestorage.dto.request.ShareRequest;
import com.example.filestorage.dto.response.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageStorageService {

    void upload(String userId, MultipartFile[] images);

    List<ImageResponse> list(String userId);

    byte[] zipAndDownloadAll(String userId, FileDownloadRequest request);

    void share(String userId, ShareRequest shareRequest);
}
