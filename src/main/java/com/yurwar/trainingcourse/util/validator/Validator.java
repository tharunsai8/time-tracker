package com.yurwar.trainingcourse.util.validator;

public interface Validator<T> {
    Result validate(T value);
}
