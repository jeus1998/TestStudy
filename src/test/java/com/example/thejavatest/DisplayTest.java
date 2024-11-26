package com.example.thejavatest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DisplayTest {
    @Test
    @DisplayName("테스트 \uD83D\uDE31")
    void display_test1(){
        System.out.println("test1");
    }
    @Test
    void display_test2(){
        System.out.println("test2");
    }
}
