package com.yurwar.trainingcourse.util.validator;

/**
 * Component interface of composite pattern
 *
 * @param <T> Value that need to be validated
 */
public interface Validator<T> {
    /**
     * @param value Value that need to be validated
     * @return result of validation
     */
    Result validate(T value);
}
