package com.wmeup.photoshare.common.utils.hibvalidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by yzf on 2016/6/3.
 * String类型的正则表达式校验
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = StringPatternValidator.class)
@Documented
public @interface StringPattern {
    String message() default "{commconf.constraints.StringPattern.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regx();
}
