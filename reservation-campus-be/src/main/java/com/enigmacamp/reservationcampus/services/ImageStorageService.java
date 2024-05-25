package com.enigmacamp.reservationcampus.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    public String storeFile(MultipartFile picture, String id_profile);
}
