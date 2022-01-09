package com.example.filestorage.service.impl;

import com.example.filestorage.dto.request.FileDownloadRequest;
import com.example.filestorage.dto.response.ImageResponse;
import com.example.filestorage.entity.Image;
import com.example.filestorage.entity.User;
import com.example.filestorage.exception.ResourceNotFoundException;
import com.example.filestorage.repository.ImageRepository;
import com.example.filestorage.repository.UserRepository;
import com.example.filestorage.service.FileService;
import com.example.filestorage.service.ImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class ImageStorageServiceImpl implements ImageStorageService {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH_mm_ss_dd_MM_yyyy");

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final FileService fileService;

    @Override
    public void upload(String userId, MultipartFile[] images) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User [" + userId + "] not found"));

        for(MultipartFile multipartFile : images) {
            try {
                String originalFilename = multipartFile.getOriginalFilename();
                String fileName = userId + "_" + DATE_FORMAT.format(new Date()) + "_" + originalFilename;
                fileService.writeImageFile(fileName, multipartFile.getBytes());
                Image image = new Image(null, originalFilename, fileName, user);
                imageRepository.save(image);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<ImageResponse> list(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User [" + userId + "] not found"));

        return user.getOwn().stream().map(this::toImageResponse).collect(toList());
    }

    @Override
    public byte[] zipAndDownloadAll(String userId, FileDownloadRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User [" + userId + "] not found"));
        Set<Long> filesId = request.getFilesId();
        if(request.isDownloadAll()) {
            filesId = user.getOwn().stream().map(Image::getId).collect(toSet());
        }

        try(/*ServletOutputStream sos = response.getOutputStream();*/
//                FileOutputStream sos = new FileOutputStream("C:\\Users\\hflbt\\Desktop\\zip.zip");
                ByteArrayOutputStream sos = new ByteArrayOutputStream();
                ZipOutputStream zos = new ZipOutputStream(sos)) {
            for(Image img : user.getOwn()) {
                Long fileId = filesId.stream().filter(id -> id.equals(img.getId())).findFirst().orElse(null);
                if(fileId == null) {
                    continue;
                }

                File fileToZip = fileService.retrieveImagePath(img.getFilename()).toFile();
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(img.getOriginalFilename());
                zos.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while((length = fis.read(bytes)) >= 0) {
                    zos.write(bytes, 0, length);
                }
                fis.close();
            }
            zos.flush();
            zos.finish();
            return sos.toByteArray();
        } catch(IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private ImageResponse toImageResponse(Image image) {
        return new ImageResponse(image.getId(), image.getOriginalFilename()/*, image.getOwner().getId()*/);
    }

}
