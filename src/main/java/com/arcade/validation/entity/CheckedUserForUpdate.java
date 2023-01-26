package com.arcade.validation.entity;

import com.arcade.entity.user.Role;
import com.arcade.validation.EmailValid;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;

// TODO: add later update functionality for this
public class CheckedUserForUpdate extends CheckedUser {

    private long id;

    @NotNull(message = "Required")
    @Size(min = 2, message = "Required")
    private String userName;

    @NotNull(message = "Required")
    @Size(min = 3, message = "Required")
    private String password;

    @NotNull(message = "Required")
    @Size(min = 3, message = "Required")
    private String matchingPassword;

    @NotNull(message = "Required")
    @Size(min = 1, message = "Required")
    private String firstName;

    @NotNull(message = "Required")
    @Size(min = 1, message = "Required")
    private String lastName;

    @NotNull(message = "Required")
    @Size(min = 3, message = "Required")
    @EmailValid
    private String email;

    private Collection<Role> roles;
    private Date createdAt;

    public CheckedUserForUpdate() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getMatchingPassword() {
        return matchingPassword;
    }

    @Override
    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
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
}
