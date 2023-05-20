package com.example.redditclone.users.controllers;

import com.example.redditclone.dtos.ErrorResponseDto;
import com.example.redditclone.dtos.OkResponseDto;
import com.example.redditclone.dtos.ResponseDto;
import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.UserRepository;
import com.example.redditclone.users.services.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("files")
public class FileController {
    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload/profilePicture/{id}")
    public ResponseEntity<ResponseDto> uploadProfilePicture(@RequestParam("File")MultipartFile multipartFile, @PathVariable long id) throws IOException {
        try {
            fileService.handleUploadPicture(multipartFile, id);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
        }
        return ResponseEntity.ok(new OkResponseDto(201, "Image successfully uploaded."));
    }
}