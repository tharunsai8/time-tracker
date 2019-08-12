package com.yurwar.trainingcourse.util.validator;

public class NotBlankValidator implements Validator<String> {
    private String message = "Value must be not blank";

    public NotBlankValidator(String message) {
        this.message = message;
    }

    public NotBlankValidator() {}

    @Override
    public Result validate(String value) {
        if (!value.isBlank()) {
            return new SimpleResult(true);
        } else {
            return new SimpleResult(false, message);
        }
    }
}
