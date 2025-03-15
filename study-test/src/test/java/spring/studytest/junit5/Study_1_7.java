package spring.studytest.junit5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spring.studytest.domain.Study;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * JUnit 5 태깅과 필터링
 */
public class Study_1_7 {
    @Test
    @DisplayName("fast")
    @Tag("fast")
    void fast() {
        var study = new Study(100);
        assertThat(study).isNotNull();
    }
    @Test
    @DisplayName("slow")
    @Tag("slow")
    void slow() {
        var study = new Study(100);
        assertThat(study).isNotNull();
    }
}

