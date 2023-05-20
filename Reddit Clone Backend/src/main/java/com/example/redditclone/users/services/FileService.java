package com.example.redditclone.users.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
    boolean handleUploadPicture(MultipartFile multipartFile, long id);
}
