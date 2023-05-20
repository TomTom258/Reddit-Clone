package com.example.redditclone.users.controllers;

import com.example.redditclone.dtos.ErrorResponseDto;
import com.example.redditclone.dtos.OkResponseDto;
import com.example.redditclone.dtos.ResponseDto;
import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("files")
public class FileController {
    private UserRepository userRepository;

    private HttpServletRequest request;

    @Autowired
    public FileController(UserRepository userRepository, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.request = request;
    }

    @PostMapping("/upload/profilePicture/{id}")
    public ResponseEntity<ResponseDto> uploadProfilePicture(@RequestParam("File")MultipartFile multipartFile, @PathVariable long id) throws IOException {
        if (!multipartFile.isEmpty()) {
            try {
                String uploadsDir = "/uploads/profilePictures/";
                String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);

                if (!new File(realPathtoUploads).exists()) {
                    new File(realPathtoUploads).mkdir();
                }

                String orgName = multipartFile.getOriginalFilename();
                String filePath = realPathtoUploads + orgName;
                User user = userRepository.getReferenceById(id);

                File dest = new File(filePath);
                multipartFile.transferTo(dest);

                user.setProfilePictureFilePath(filePath);
                userRepository.save(user);
            } catch (ResponseStatusException e) {
                return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponseDto(e.getReason()));
            }
        }
        return ResponseEntity.ok(new OkResponseDto(201, "Image successfully uploaded."));
    }
}