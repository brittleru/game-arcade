package com.arcade.dto;

import com.arcade.validation.EmailValid;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserBasicInfoDto extends BaseDto {

    // TODO: implement dto and converter for user details for admin and for user itself

    @NotNull(message = "Username can't be empty")
    @Size(min = 2, message = "Required")
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return "UserBasicInfoDto{" +
                "id=" + this.getId() +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
