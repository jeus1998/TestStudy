package spring.studytest.junit5;

import org.junit.jupiter.api.*;
import spring.studytest.domain.Study;
import spring.studytest.domain.StudyStatus;

import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {
    @Test
    @DisplayName("스터디 만들기 \uD83D\uDE31")
    void create_new_study_assertAll() {
        var study = new Study(10);
        assertAll(
                () -> assertNotNull(study),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야함"),
                () -> Assertions.assertEquals(StudyStatus.DRAFT, study.getStatus(),
                        () -> "스터디를 처음 만들면 상태값이 " + StudyStatus.DRAFT)
        );
    }
    @Test
    @DisplayName("스터디 만들기 assertThrows")
    void create_new_study_assertThrows(){
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals("limit 0보다 커야 한다.", exception.getMessage());
    }
    @Test
    @DisplayName("스터디 만들기 assertTimeout")
    void create_new_study_assertTimeout(){
        assertTimeout(Duration.ofMillis(100), () -> {
             new Study(10);
             Thread.sleep(50);
        }, () -> "스터디 생성은 0.1초 이내에 동작");
    }

    @Test
    @DisplayName("스터디 만들기 assertTimeoutPreemptively")
    void create_new_study_assertTimeoutPreemptively(){
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
             new Study(10);
             Thread.sleep(50);
        }, () -> "스터디 생성은 0.1초 이내에 동작");
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