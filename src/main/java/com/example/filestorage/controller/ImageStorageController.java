package com.example.filestorage.controller;

import com.example.filestorage.dto.response.ImageResponse;
import com.example.filestorage.entity.Image;
import com.example.filestorage.entity.User;
import com.example.filestorage.exception.ResourceNotFoundException;
import com.example.filestorage.repository.ImageRepository;
import com.example.filestorage.repository.UserRepository;
import com.example.filestorage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageStorageController {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH_mm_ss_dd_MM_yyyy");

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final FileService fileService;

    @PostMapping
    public void upload(@RequestHeader("User-Id") String userId, @RequestParam("files") MultipartFile[] images) {
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

    @GetMapping
    public List<ImageResponse> list(@RequestHeader("User-Id") String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User [" + userId + "] not found"));

        return user.getOwn().stream().map(this::toImageResponse).collect(toList());
    }

    private ImageResponse toImageResponse(Image image) {
        return new ImageResponse(image.getId(), image.getOriginalFilename()/*, image.getOwner().getId()*/);
    }

//    @PostMapping(path = "/abc")
//    public void upload(@RequestHeader("User-Id") String userId, @RequestParam("files") MultipartFile[] images) {
//        System.out.println();
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User [" + userId + "] not found"));
//
//
//
//        userRepository.save(user);
//    }
}
