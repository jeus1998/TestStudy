package com.example.thejavatest.instance;

import org.junit.jupiter.api.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestInstancePractice {
    @BeforeAll
    void beforeAll(){
        System.out.println(this.hashCode());
    }
    @AfterAll
    void afterAll(){
        System.out.println(this.hashCode());
    }
    @Test
    void test1(){
        System.out.println(this.hashCode());
    }
    @Test
    void test2(){
        System.out.println(this.hashCode());
    }
}
