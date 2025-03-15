package spring.studytest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * JUnit 5 조건에 따라 테스트 실행하기
 */
public class Study_1_6 {
    @Test
    @DisplayName("assumeTrue")
    void test(){
        var testEnv = System.getenv("TEST_ENV");
        assumeTrue("LOCAL2".equalsIgnoreCase(testEnv));
        var study = new Study(10);
        assertThat(study.getLimit()).isGreaterThan(5);
    }

    @Test
    @DisplayName("assumingThat")
    void test2(){
        var testEnv = System.getenv("TEST_ENV");
        assumingThat("LOCAL2".equalsIgnoreCase(testEnv), () -> {
            var study = new Study(10);
            assertThat(study.getLimit()).isGreaterThan(5);
        });
    }

    @Test
    @DisplayName("@EnabledOnOS")
    @EnabledOnOs({OS.MAC, OS.LINUX})
    void test3(){
        var study = new Study(10);
        assertThat(study.getLimit()).isGreaterThan(5);
    }

    @Test
    @DisplayName("@EnabledIfEnvironmentVariable")
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL2")
    void test4(){
        var study = new Study(10);
        assertThat(study.getLimit()).isGreaterThan(5);
    }

}
