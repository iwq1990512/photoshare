package com.wmeup.photoshare.common.utils.hibvalidator;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yzf on 2016/6/3.
 */
public class IntegerPatternValidator implements ConstraintValidator<IntegerPattern, Integer> {
    String regx;

    public void initialize(IntegerPattern constraintAnnotation) {
        this.regx = constraintAnnotation.regx();
    }

    public boolean isValid(Integer object, ConstraintValidatorContext constraintValidatorContext) {
        if (object==null){
            return false;
        }
        //正则表达式不为空则匹配
        if (StringUtils.isNotBlank(regx)){
            String objStr = object.toString();
            Pattern pattern = Pattern.compile(regx);
            Matcher matcher = pattern.matcher(objStr);
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }
}
