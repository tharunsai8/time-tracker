package com.yurwar.trainingcourse.util.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CompositeValidator<T> extends AbstractCompositeValidator<T> {
    private List<Validator<T>> validatorList = new ArrayList<>();

    public CompositeValidator(Collection<Validator<T>> validatorList) {
        this.validatorList.addAll(validatorList);
    }

    public CompositeValidator(Validator<T>... validators) {
        this.validatorList.addAll(Arrays.asList(validators));
    }

    public CompositeValidator() {
    }

    @Override
    public void addValidator(Validator<T> validator) {
        validatorList.add(validator);
    }

    @Override
    public void removeValidator(Validator<T> validator) {
        validatorList.remove(validator);
    }

    @Override
    public Result validate(T value) {
        CompositeResult result = new CompositeResult();
        validatorList.stream().map(validator -> validator.validate(value)).forEach(result::addResult);
        return result;
    }
}