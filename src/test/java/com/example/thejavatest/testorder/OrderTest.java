package com.example.thejavatest.testorder;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderTest {
    @Test
    @Order(3)
    void test1(){

    }
    @Test
    @Order(2)
    void test2(){

    }
    @Test
    @Order(1)
    void test3(){

    }
}
