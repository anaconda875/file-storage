package com.example.filestorage.service.impl;

import com.example.filestorage.dto.response.LoginResponse;
import com.example.filestorage.entity.User;
import com.example.filestorage.exception.ResourceNotFoundException;
import com.example.filestorage.repository.UserRepository;
import com.example.filestorage.service.LoginService;
import com.example.filestorage.util.ChecksumUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    @Override
    public LoginResponse login(byte[] key) {
        try {
            SecretKey sKey = new SecretKeySpec(key, "AES");
            String hash = ChecksumUtil.getChecksum(sKey);
            User user = userRepository.findById(hash).orElseThrow(() -> new ResourceNotFoundException("User [" + hash + "] not found"));

            return new LoginResponse(user.getId(), user.getName());
        } catch(IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
