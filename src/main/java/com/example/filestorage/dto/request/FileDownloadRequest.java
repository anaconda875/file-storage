package com.example.filestorage.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class FileDownloadRequest {

    private boolean downloadAll;

    private Set<Long> filesId;

}
