package com.example.thejavatest;

import com.example.thejavatest.domain.Study;
import com.example.thejavatest.domain.StudyStatus;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;


class StudyTest {
    @Test
    @DisplayName("스터디 만들기")
    void create_new_study(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals( "limit 0보다 커야 한다.", exception.getMessage());
    }
    @Test
    @Disabled
    void create2(){
        Study study = new Study();
        assertAll(
            () -> assertNotNull(study),
            // () -> assertTrue(study.getLimit() > 0, "스터디 참석 가능 인원은 0보다 커야 한다"),
            () ->  assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 DRAFT 여야 한다")
        );
    }
    @Test
    void assert_time_out(){
       assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
           Study study = new Study();
           Thread.sleep(1000);
       });
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