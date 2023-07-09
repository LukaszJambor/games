package com.example2.demo.validators.impl;

import com.example2.demo.model.enums.DistributionPath;
import com.example2.demo.validators.DistributionPathValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DistributionPathValidatorImpl implements ConstraintValidator<DistributionPathValidator, DistributionPath> {

    @Override
    public void initialize(DistributionPathValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(DistributionPath distributionPath, ConstraintValidatorContext constraintValidatorContext) {
        return distributionPath != null;
    }
}
