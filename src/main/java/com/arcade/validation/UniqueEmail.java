package com.arcade.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface UniqueEmail {

    public String message() default " address already exists";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

}
