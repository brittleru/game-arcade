package com.arcade.dao.user.repository;

import com.arcade.entity.user.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageStorageRepository extends JpaRepository<UserImage, Long> {

    Optional<UserImage> findByFileName(String fileName);
    Optional<UserImage> findById(long id);

    void deleteById(long id);

}
