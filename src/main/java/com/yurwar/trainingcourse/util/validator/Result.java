package com.yurwar.trainingcourse.util.validator;

/**
 * Component result of validation
 * Interface of composite pattern
 */
public interface Result {
    /**
     * @return if validated value is valid
     */
    boolean isValid();

    /**
     * @return get validation result message
     */
    String getMessage();
}
