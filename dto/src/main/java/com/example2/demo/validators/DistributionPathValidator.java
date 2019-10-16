package com.example2.demo.validators;

import com.example2.demo.validators.impl.DistributionPathValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DistributionPathValidatorImpl.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributionPathValidator {
    String message() default "provide distribution path";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
