package com.example.filestorage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {

    private Long id;
    private String originalFilename;
//    private String owner;

}
