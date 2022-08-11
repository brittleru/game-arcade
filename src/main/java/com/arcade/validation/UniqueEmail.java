package com.arcade.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface UniqueEmail {

    String message() default " address already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
