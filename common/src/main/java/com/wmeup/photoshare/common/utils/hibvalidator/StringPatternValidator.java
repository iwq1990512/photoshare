package com.wmeup.photoshare.common.utils.hibvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yzf on 2016/6/3.
 */
public class StringPatternValidator implements ConstraintValidator<StringPattern, String> {
    String regx;

    public void initialize(StringPattern constraintAnnotation) {
        this.regx = constraintAnnotation.regx();
    }

    public boolean isValid(String object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(object);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }
}
