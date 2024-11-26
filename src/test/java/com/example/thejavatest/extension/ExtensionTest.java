package com.example.thejavatest.extension;

import com.example.thejavatest.customtag.SlowTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

// @ExtendWith(FindSlowTestExtension.class)
class ExtensionTest {

    @RegisterExtension
    FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);
    @Test
    void test1(){
        System.out.println("test1");
    }
    @SlowTest
    void test2() throws InterruptedException{
        System.out.println("test2");
        Thread.sleep(1005L);
    }
    @Test
    void test3() throws InterruptedException{
        System.out.println("test3");
        Thread.sleep(1005L);
    }
}
