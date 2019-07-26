package org.gwhere.utils;

import org.gwhere.exception.ErrorCode;
import org.gwhere.exception.SystemException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collection;
import java.util.Set;

/**
 * 验证工具类
 */
public class ValidateUtil {

    /**
     * 验证单个对象
     *
     * @param t
     * @param <T>
     */
    public static <T> void validate(T t) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        for (ConstraintViolation<T> violation : violations) {
            throw new SystemException(ErrorCode.VALID_FAILED, violation.getMessage());
        }
    }

    /**
     * 验证对象集合
     *
     * @param collection
     * @param <T>
     */
    public static <T> void validate(Collection<T> collection) {
        for(T t : collection) {
            validate(t);
        }
    }
}
