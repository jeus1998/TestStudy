package com.example.thejavatest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TagTest {
    @Test
    @Tag("fast")
    void test1(){
        System.out.println("fast");
    }
    @Test
    @Tag("slow")
    void test2(){
        System.out.println("slow");
    }
}
