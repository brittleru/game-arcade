package com.arcade.validation;

import com.arcade.entity.user.User;
import com.arcade.service.rest.AdminService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameDifferentByUpdatedValidator implements ConstraintValidator<UniqueUsernameDifferentByUpdated, Object> {

    private final AdminService adminService;
    private String id;
    private String field;
    private String message;

    @Autowired
    public UniqueUsernameDifferentByUpdatedValidator(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void initialize(final UniqueUsernameDifferentByUpdated constraintAnnotation) {
        id = constraintAnnotation.first();
        field = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object o, final ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        try {
            final Object firstObject = new BeanWrapperImpl(o).getPropertyValue(id);
            final Object secondObject = new BeanWrapperImpl(o).getPropertyValue(field);
            User user = adminService.getUserByUsernameIfDifferentById(String.valueOf(secondObject), Integer.parseInt(String.valueOf(firstObject)));
            valid = user != null;
        }
        catch (final Exception ignored) {
            ;
        }
        if (!valid) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(id)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
