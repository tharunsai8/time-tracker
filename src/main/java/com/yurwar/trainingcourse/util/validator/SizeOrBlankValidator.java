package com.yurwar.trainingcourse.util.validator;

public class SizeOrBlankValidator extends SizeValidator {
    public SizeOrBlankValidator(int minSize, int maxSize) {
        super(minSize, maxSize);
    }

    public SizeOrBlankValidator(int minSize, int maxSize, String message) {
        super(minSize, maxSize, message);
    }

    @Override
    public Result validate(String value) {
        if (value.isBlank()) {
            return new SimpleResult(true);
        } else {
            return super.validate(value);
        }
    }
}
