package com.dlnu.utils;

import com.dlnu.config.MyPasswordEncoder;
import org.junit.jupiter.api.Test;

public class TestAll {
    @Test
    public void test() {
        MyPasswordEncoder mpe = new MyPasswordEncoder();
        String encode = mpe.encode("123");
        System.out.println(encode);
    }
}
