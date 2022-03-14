package com.arcade.dto;

import com.arcade.entity.user.Role;
import com.arcade.validation.EmailValid;
import com.arcade.validation.UniqueEmail;
import com.arcade.validation.UniqueUsername;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;

public class UserDto extends BaseDto {

    @NotNull(message = "Username can't be empty")
    @Size(min = 2, message = "Required")
    private String username;

    @NotNull(message = "Required")
    @Size(min = 3, message = "Required")
    private String password;

    @NotNull(message = "First Name can't be empty")
    @Size(min = 1, message = "Required")
    private String firstName;

    @NotNull(message = "Last Name can't be empty")
    @Size(min = 1, message = "Required")
    private String lastName;

    @NotNull(message = "Email can't be empty")
    @Size(min = 3, message = "Required")
    @EmailValid
    private String email;

    private Date createdAt;

    private Date updatedAt;

    private Collection<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + this.getId() +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", roles=" + roles +
                '}';
    }

}
