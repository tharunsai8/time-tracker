package com.yurwar.trainingcourse.util.validator;

public class PositiveOrZeroValidator implements Validator<Integer> {
    private String message = "Value must be positive or zero";

    public PositiveOrZeroValidator(String message) {
        this.message = message;
    }

    public PositiveOrZeroValidator() {
    }

    @Override
    public Result validate(Integer value) {
        if (value >= 0) {
            return new SimpleResult(true);
        } else {
            return new SimpleResult(false, message);
        }
    }
}
