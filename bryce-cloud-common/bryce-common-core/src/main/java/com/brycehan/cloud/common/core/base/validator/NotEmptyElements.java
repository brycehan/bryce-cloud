package com.brycehan.cloud.common.core.base.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 集合元素不为空
 *
 * @author Bryce Han
 * @since 2023/5/31
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyElementsValidator.class)
public @interface NotEmptyElements {

    String message() default "元素不能为空";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
