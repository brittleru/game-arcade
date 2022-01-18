package com.arcade.validateentity;

import com.arcade.validation.FieldMatch;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({
        @FieldMatch(
                first = "password",
                second = "matchingPassword",
                message = "The password fields must match."
        )
})
public class CheckedUser {

    @NotNull(message = "Required")
    @Size(min = 1, message = "Required")
    private String userName;

    @NotNull(message = "Required")
    @Size(min = 1, message = "Required")
    private String password;

    @NotNull(message = "Required")
    @Size(min = 1, message = "Required")
    private String matchingPassword;

    @NotNull(message = "Required")
    @Size(min = 1, message = "Required")
    private String firstName;

    @NotNull(message = "Required")
    @Size(min = 1, message = "Required")
    private String lastName;

    @NotNull(message = "Required")
    @Size(min = 1, message = "Required")
    private String email;


    public CheckedUser() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
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
}
