package com.yurwar.trainingcourse.util.validator;

abstract class AbstractCompositeValidator<T> implements Validator<T> {
    abstract void addValidator(Validator<T> validator);

    abstract void removeValidator(Validator<T> validator);
}
