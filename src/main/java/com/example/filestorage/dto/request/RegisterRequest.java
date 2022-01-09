package com.example.filestorage.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterRequest {

    @NotBlank
    private String name;

    private byte[] key;

}
