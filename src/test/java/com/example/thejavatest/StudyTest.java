package com.example.thejavatest;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


class StudyTest {
    @Test
    void create1(){
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create1");
    }
    @Test
    @Disabled
    void create2(){
        System.out.println("create2");
    }
    @BeforeAll
    static void beforeAll(){
        System.out.println("before all");
    }
    @AfterAll
    static void afterAll(){
        System.out.println("after all");
    }
    @BeforeEach
    void beforeEach(){
        System.out.println("before each");
    }
    @AfterEach
    void afterEach(){
        System.out.println("after each");
    }
}