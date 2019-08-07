package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.entity.ActivityStatus;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegistrationCommandTest {

    @Test
    public void testMD5() {
        for (int i = 0; i < 10; i++) {
            System.out.println(DigestUtils.md5Hex("123456789"));
        }
    }

    @Test
    public void testEnum() {
        System.out.println(ActivityStatus.valueOf(null));
    }

}