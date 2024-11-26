package com.example.thejavatest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class ConditionTest {
    @Test
    @EnabledOnOs({OS.MAC, OS.LINUX, OS.WINDOWS})
    void test1(){
        System.out.println("TEST1");
        String testEnv = System.getenv("TEST_ENV");
        System.out.println(testEnv);
        assumeTrue("LOCAL".equalsIgnoreCase(testEnv));

        Study study = new Study(10);
        assertThat(study.getLimit()).isGreaterThan(9);
    }
    @Test @DisplayName("assumingThat")
    @DisabledOnOs({OS.WINDOWS})
    void test2(){
        System.out.println("TEST2");
        String testEnv = System.getenv("TEST_ENV");

        assumingThat("LOCAL".equalsIgnoreCase(testEnv), () -> {
            System.out.println("LOCAL");
            Study study = new Study(10);
            assertThat(study.getLimit()).isGreaterThan(9);
        });

        assumingThat("TEST".equalsIgnoreCase(testEnv), () -> {
            System.out.println("TEST");
            Study study = new Study(10);
            assertThat(study.getLimit()).isGreaterThan(9);
        });
    }
    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void test3(){
        System.out.println("test3");
    }
    @Test
    @EnabledOnJre({JRE.JAVA_11, JRE.JAVA_8})
    void test4(){
        System.out.println("test4");
    }
    @Test
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void test5(){
       System.out.println("test5");
    }
    @Test
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "TEST")
    void test6(){
       System.out.println("test6");
    }
}
