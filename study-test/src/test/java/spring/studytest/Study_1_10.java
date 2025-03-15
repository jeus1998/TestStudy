package spring.studytest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * JUnit5 테스트 인스턴스
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Study_1_10 {
    @Test
    public void test1() {
        System.out.println(this);
    }
    @Test
    public void test2() {
        System.out.println(this);
    }
}
