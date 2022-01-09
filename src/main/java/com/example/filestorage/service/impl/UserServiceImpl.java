package com.example.filestorage.service.impl;

import com.example.filestorage.dto.request.RegisterRequest;
import com.example.filestorage.dto.response.RegisterResponse;
import com.example.filestorage.dto.response.UserResponse;
import com.example.filestorage.entity.User;
import com.example.filestorage.exception.DuplicatedEntityException;
import com.example.filestorage.repository.UserRepository;
import com.example.filestorage.service.FileService;
import com.example.filestorage.service.UserService;
import com.example.filestorage.util.ChecksumUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FileService fileService;

    public RegisterResponse register(RegisterRequest registerRequest) {
        try {
            SecretKey key = new SecretKeySpec(registerRequest.getKey(), "AES");
            String hash = ChecksumUtil.getChecksum(key);
            userRepository.findById(hash).ifPresent(u -> {
                throw new DuplicatedEntityException("Duplicated user found [" + u.getId() + "]");
            });

            fileService.writeAesFile(hash + ".key", registerRequest.getKey());
            User user = new User(hash, registerRequest.getName(), hash + ".key", null);
            user = userRepository.save(user);

            return new RegisterResponse(new UserResponse(user.getId(), user.getName()));
        } catch(IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

//    public UserResponse findById(String id) {
//        return userRepository.findById(id).map(this::toResponse).orElseThrow(() -> new ResourceNotFoundException("User [" + id + "] not found"));
//    }
//
//    private UserResponse toResponse(User user) {
//        return new UserResponse(user.getId(), user.getName());
//    }

}