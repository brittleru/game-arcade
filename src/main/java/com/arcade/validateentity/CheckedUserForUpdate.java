package com.arcade.validateentity;

import com.arcade.entity.user.Role;
import com.arcade.validation.EmailValid;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;

public class CheckedUserForUpdate extends CheckedUser {

    private int id;
    private Collection<Role> roles;
    private Date createdAt;

    @NotNull(message = "Required")
    @Size(min = 2, message = "Required")
//    @UniqueUsername
    private String userName;

    @NotNull(message = "Required")
    @Size(min = 3, message = "Required")
    @EmailValid
//    @UniqueEmail
    private String email;

    public CheckedUserForUpdate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
}
