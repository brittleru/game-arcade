package com.arcade.controller.rest;

import com.arcade.entity.user.User;
import com.arcade.service.rest.AdminService;
import com.arcade.service.rest.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/profile/picture")
public class ProfilePictureController {

    private final ImageStorageService service;
    private final AdminService adminService;

    @Autowired
    public ProfilePictureController(ImageStorageService service, AdminService adminService) {
        this.service = service;
        this.adminService = adminService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("profileImage") MultipartFile file) throws IOException {
        String response = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> getImage(@PathVariable String fileName) {
        byte[] imageData = service.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }

    @GetMapping("/profile-image/{username}")
    public String getUserImage(@PathVariable("username") String username) {
        User user = adminService.findUserByUsername(username);
        if (user.getUserImage() != null) {
            return user.getUserImage().getFileName();
        }
        return null;
    }
}
