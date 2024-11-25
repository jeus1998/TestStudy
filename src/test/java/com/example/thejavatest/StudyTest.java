package com.example.thejavatest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class StudyTest {
    @Test
    void create(){
        Study study = new Study();
        assertNotNull(study);
    }
}