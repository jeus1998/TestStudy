package com.example.thejavatest.customtag;

import org.junit.jupiter.api.Test;
class CustomTagTest {
    @Test @FastTest
    void fast(){
        System.out.println("fast");
    }
    @Test @SlowTest
    void slow(){
        System.out.println("slow");
    }
}
