package com.yurwar.trainingcourse.controller.command;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class RegistrationCommandTest {

    @Test
    public void testMD5() {
        for (int i = 0; i < 10; i++) {
            System.out.println(DigestUtils.md5Hex("123456789"));
        }
    }

    @Test
    public void testEnum() {
    }

}