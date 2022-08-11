package com.arcade.dao.user;

import com.arcade.entity.user.Role;

public interface RoleDao {

    Role findRoleByName(String roleName);

}
