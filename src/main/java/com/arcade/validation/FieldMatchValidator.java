package com.arcade.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object o, final ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        try {
            final Object firstObject = new BeanWrapperImpl(o).getPropertyValue(firstFieldName);
            final Object secondObject = new BeanWrapperImpl(o).getPropertyValue(secondFieldName);

            valid = firstObject == null && secondObject == null || firstObject != null && firstObject.equals(secondObject);
        } catch (final Exception ignored) {
            ;
        }

        if (!valid) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
