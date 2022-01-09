package com.example.filestorage.service;

import java.io.IOException;
import java.nio.file.Path;

public interface FileService {

    Path writeAesFile(String filename, byte[] bytes) throws IOException;

    Path writeImageFile(String filename, byte[] bytes) throws IOException;

    Path retrieveImagePath(String filename);

}
