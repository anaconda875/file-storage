package com.example.filestorage.service;

import java.io.IOException;
import java.nio.file.Path;

public interface FileService {

    Path writeAesFile(String fileName, byte[] bytes) throws IOException;

    Path writeImageFile(String fileName, byte[] bytes) throws IOException;


}
