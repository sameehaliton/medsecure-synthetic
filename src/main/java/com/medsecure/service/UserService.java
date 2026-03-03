package com.medsecure.service;

import com.medsecure.repository.UserRepository;
import java.io.File;
import java.nio.file.Files;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Passes unsanitized input through to the repository layer
    public String findUser(String userId) {
        return userRepository.getUserById(userId);
    }

    // CWE-22: passes user-controlled path directly to File without validation
    public byte[] readDocument(String filePath) throws Exception {
        File f = new File("/data/documents/" + filePath);  // path traversal sink
        return Files.readAllBytes(f.toPath());
    }
}
