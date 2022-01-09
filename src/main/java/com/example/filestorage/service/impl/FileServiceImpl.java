package com.example.filestorage.service.impl;

import com.example.filestorage.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    @Value("${app.aes.location}")
    private String aesLocation;

    @Value("${app.image.location}")
    private String imageLocation;

    @PostConstruct
    public void init() throws IOException {
        Path aes = Paths.get(aesLocation);
        if(!Files.exists(aes)) {
            Files.createDirectories(aes);
        }

        Path image = Paths.get(imageLocation);
        if(!Files.exists(image)) {
            Files.createDirectories(image);
        }
    }

    @Override
    public Path writeAesFile(String filename, byte[] bytes) throws IOException {
        return Files.write(Paths.get(aesLocation, filename), bytes);
    }

    @Override
    public Path writeImageFile(String filename, byte[] bytes) throws IOException {
        return Files.write(Paths.get(imageLocation, filename), bytes);
    }

    @Override
    public Path retrieveImagePath(String filename) {
        return Paths.get(imageLocation, filename);
    }

}
