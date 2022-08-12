package com.arcade.validateentity;

import com.arcade.entity.user.Role;

import java.util.Collection;

public class CheckedUserForAdmin extends CheckedUser {

    private int id;
    private Collection<Role> roles;


    public CheckedUserForAdmin() {

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
}
