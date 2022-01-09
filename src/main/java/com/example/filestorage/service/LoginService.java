package com.example.filestorage.service;

import com.example.filestorage.dto.response.LoginResponse;

public interface LoginService {

    LoginResponse login(byte[] key);

}
