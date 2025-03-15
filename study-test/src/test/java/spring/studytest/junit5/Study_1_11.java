package spring.studytest.junit5;

import org.junit.jupiter.api.*;

/**
 * JUnit 5 테스트 순서
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Study_1_11 {
    @Order(1)
    @Test
    void test1() {
        System.out.println(1);
    }

    @Order(2)
    @Test
    void test2() {
        System.out.println(2);
    }

    @Order(3)
    @Test
    void test3() {
        System.out.println(3);
    }
}
