package com.jay.scourse.validator;


import com.jay.scourse.annotation.IsMobile;
import com.jay.scourse.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Jay
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean require = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        this.require = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        if(require){
            return ValidatorUtil.isMobileValidate(str);
        }
        else{
            if(str == null) {
                return true;
            }
            else{
                return ValidatorUtil.isMobileValidate(str);
            }
        }
    }
}
