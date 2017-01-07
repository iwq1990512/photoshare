package com.wmeup.photoshare.common.utils.hibvalidator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

/**
 * Created by yzf on 2016/6/3.
 * 提供获取xml校验返回信息
 */
public class HibValidatorUtil {
    private Validator validator;

    public void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

   /* public void setValidator(Validator validator) {
        this.validator = validator;
    }*/

    /*
           验证指定对象的全部属性
         */
    public String getFullValidatorMessage(Object object) {
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(object);
        if (!CollectionUtils.isEmpty(constraintViolationSet)&&StringUtils.isNotBlank(constraintViolationSet.iterator().next().getMessage())) {
            return constraintViolationSet.iterator().next().getMessage();
        }
        return null;
    }

    /*
       验证指定对象的某些属性
     */
    public String getPropertyValidatorMessage(Object object, List<String> propertyList) {
        String message = "";
        if (!CollectionUtils.isEmpty(propertyList)) {
            for (String property : propertyList) {
                Set<ConstraintViolation<Object>> constraintViolationSet = validator.validateProperty(object, property);
                if (!CollectionUtils.isEmpty(constraintViolationSet)&&StringUtils.isNotBlank(constraintViolationSet.iterator().next().getMessage())) {
                    return constraintViolationSet.iterator().next().getMessage();
                }
            }

        }
        return message;

    }
}
