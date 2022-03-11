package com.arcade.dto;

import com.arcade.entity.user.Role;

import java.util.Collection;
import java.util.Date;

public class UserDto extends BaseDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Date createdAt;
    private Date updatedAt;
    private Collection<Role> roles;

}
