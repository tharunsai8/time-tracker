package com.yurwar.trainingcourse.util.validator;

/**
 * Abstract class that need to be implemented by composite validator class
 *
 * @param <T> Value that need to be validated
 */
abstract class AbstractCompositeValidator<T> implements Validator<T> {
    /**
     * Add validator to composite
     *
     * @see Validator
     */
    abstract void addValidator(Validator<T> validator);

    /**
     * Remove validator from composite
     * @see Validator
     */
    abstract void removeValidator(Validator<T> validator);
}
