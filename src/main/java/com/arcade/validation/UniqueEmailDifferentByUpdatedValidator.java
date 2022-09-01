package com.arcade.validation;

import com.arcade.service.rest.AdminService;
import com.arcade.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailDifferentByUpdatedValidator implements ConstraintValidator<UniqueEmailDifferentByUpdated, String> {

    private final AdminService adminService;
    private final UserService userService;

    @Autowired
    public UniqueEmailDifferentByUpdatedValidator(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
//        User user = adminService.getUserByEmailIfDifferentById();
//
//        User user = userService.emailExists(s);
        return s != null && !userService.emailExists(s);
    }
}
