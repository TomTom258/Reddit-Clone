package com.example.redditclone.users.services;

import com.example.redditclone.users.models.User;
import com.example.redditclone.users.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileServiceImp implements FileService {
    private UserRepository userRepository;
    private HttpServletRequest httpServletRequest;

    @Autowired
    public FileServiceImp(UserRepository userRepository, HttpServletRequest httpServletRequest) {
        this.userRepository = userRepository;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public boolean handleUploadPicture(MultipartFile multipartFile, long id) {
        String fileName = multipartFile.getOriginalFilename();
        String mimeType = httpServletRequest.getServletContext().getMimeType(fileName);
        User user = userRepository.getReferenceById(id);

        if (Objects.isNull(user.getVerifiedAt())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must have email validated first!");
        }

        if (!multipartFile.isEmpty() && mimeType.startsWith("image/")) {
            try {
                String uploadsDir = "/uploads/profilePictures/";
                String realPathtoUploads = httpServletRequest.getServletContext().getRealPath(uploadsDir);

                if (!new File(realPathtoUploads).exists()) {
                    new File(realPathtoUploads).mkdirs();
                }

                String orgName = multipartFile.getOriginalFilename();
                String extension = orgName.substring(orgName.lastIndexOf(".") + 1);
                String newFileName = UUID.randomUUID().toString() + "." + extension;
                String filePath = realPathtoUploads + newFileName;

                while (userRepository.existsByProfilePictureFilePath(filePath)) {
                    newFileName = UUID.randomUUID().toString() + "." + extension;
                    filePath = realPathtoUploads + newFileName;
                }

                if (!user.getProfilePictureFilePath().isEmpty()) {
                    File oldPicture = new File(user.getProfilePictureFilePath());
                    oldPicture.delete();
                }

                File dest = new File(filePath);
                multipartFile.transferTo(dest);
                Thumbnails.of(filePath).scale(0.4).outputQuality(0.5).toFile(filePath);

                user.setProfilePictureFilePath(filePath);
                userRepository.save(user);
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty! ");
        }
    }
}
