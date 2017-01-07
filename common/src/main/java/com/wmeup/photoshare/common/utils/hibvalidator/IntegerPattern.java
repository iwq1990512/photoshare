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
 * integer类型的正则表达式校验
 */
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = IntegerPatternValidator.class)
@Documented
public @interface IntegerPattern {
    String message() default "{commconf.constraints.IntegerPattern.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default  {};
    String regx() default "";
}
