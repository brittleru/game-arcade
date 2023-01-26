package com.arcade.service.rest;

import com.arcade.dao.user.repository.ImageStorageRepository;
import com.arcade.entity.user.User;
import com.arcade.entity.user.UserImage;
import com.arcade.process.ImageProcessing;
import com.arcade.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ImageStorageService {
    private final static Logger logger = Logger.getLogger(ImageStorageService.class.getName());

    private final ImageStorageRepository imageStorageRepository;
    private final UserService userService;

    @Autowired
    public ImageStorageService(ImageStorageRepository imageStorageRepository, UserService userService) {
        this.imageStorageRepository = imageStorageRepository;
        this.userService = userService;
    }

    public UserImage getUserImageById(long id) {
        Optional<UserImage> userImage = imageStorageRepository.findById(id);
        return userImage.orElse(null);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        Optional<String> extension = getExtension(file.getOriginalFilename());
        String fileName = user.getUsername() + "_profile_picture.";
        if (extension.isPresent()) {
            fileName += extension.get();
        } else {
            fileName += "png";
        }

        if (Objects.nonNull(user.getUserImage())) {
            UserImage tempUserImage = user.getUserImage();
            user.setUserImage(null);
            imageStorageRepository.deleteById(tempUserImage.getId());
        }

        // TODO: change file name (all to png), img size and the content type (to png)
        UserImage userImage = new UserImage();
        userImage.setFileName(fileName);
        userImage.setType(file.getContentType());
        userImage.setImageData(ImageProcessing.compressImage(file.getBytes()));
        user.setUserImage(userImage);
        userImage.setUser(user);

        UserImage userImageRepository = imageStorageRepository.save(userImage);

        if (userImageRepository != null) {
            logger.info(String.format("Profile image %s uploaded successfully", userImageRepository.getFileName()));
            return "Image uploaded successfully " + userImageRepository.getFileName();
        }

        return "Couldn't upload the image";
    }

    public byte[] downloadImage(String fileName) {
        Optional<UserImage> imageData = imageStorageRepository.findByFileName(fileName);
        if (imageData.isPresent()) {
            return ImageProcessing.decompressImage(imageData.get().getImageData());
        }
        throw new RuntimeException(String.format("Image %s not found...", fileName));
    }


    private Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
