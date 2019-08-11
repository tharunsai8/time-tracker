package com.yurwar.trainingcourse.util.validator;

import com.yurwar.trainingcourse.model.entity.Authority;

import java.util.Collection;

public class CollectionMinSizeValidator implements Validator<Collection<Authority>> {
    private int minSize;
    private String message = "Collection size must be from " + minSize + " elements length";

    public CollectionMinSizeValidator(int minSize) {
        this.minSize = minSize;
    }

    public CollectionMinSizeValidator(int minSize, String message) {
        this.minSize = minSize;
        this.message = message;
    }

    @Override
    public Result validate(Collection<Authority> value) {
        if (value.size() >= minSize) {
            return new SimpleResult(true);
        } else {
            return new SimpleResult(false, message);
        }
    }
}
