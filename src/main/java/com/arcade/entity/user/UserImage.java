package com.arcade.entity.user;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "user_image")
public class UserImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "type")
    private String type;

    @Column(name = "image_data")
    @Lob
    private byte[] imageData;

    @OneToOne(mappedBy = "userImage")
    private User user;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    public UserImage() {
    }

    public UserImage(Long id, String fileName, String type, byte[] imageData, Date createdAt, Date updatedAt) {
        this.id = id;
        this.fileName = fileName;
        this.type = type;
        this.imageData = imageData;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UserImage(Long id, String fileName, String type, byte[] imageData, User user, Date createdAt, Date updatedAt) {
        this.id = id;
        this.fileName = fileName;
        this.type = type;
        this.imageData = imageData;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "UserImage{" +
                "id=" + id +
                ", name='" + fileName + '\'' +
                ", type='" + type + '\'' +
                ", imageData=" + Arrays.toString(imageData) +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
