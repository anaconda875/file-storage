package com.example.filestorage.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class ShareRequest {

    private Set<Long> filesId;
    private String userIdToShare;

}
