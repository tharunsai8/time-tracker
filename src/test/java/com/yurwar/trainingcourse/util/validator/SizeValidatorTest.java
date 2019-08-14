package com.yurwar.trainingcourse.util.validator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SizeValidatorTest {
    private SizeValidator sizeValidator;

    @Before
    public void init() {
        sizeValidator = new SizeValidator(3, 5);
    }

    @Test
    public void validateValidData() {
        Result result = sizeValidator.validate("abc");

        assertTrue(result.isValid());
    }

    @Test
    public void validateInvalidData() {
        Result lowerResult = sizeValidator.validate("a");
        Result upperResult = sizeValidator.validate("abcdefg");

        assertFalse(lowerResult.isValid());
        assertFalse(upperResult.isValid());
    }
}