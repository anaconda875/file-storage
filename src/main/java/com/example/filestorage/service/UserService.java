package com.example.filestorage.service;

import com.example.filestorage.dto.request.RegisterRequest;
import com.example.filestorage.dto.response.RegisterResponse;

public interface UserService {

    RegisterResponse register(RegisterRequest registerRequest);

//    UserResponse findById(String id);

}
