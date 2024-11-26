package com.example.thejavatest.customtag;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * FastTest 애노테이션은
 * @Test + @Tag("fast") 애노테이션을 결합한 composed 애노테이션
 * 즉, 새로운 메타 애노테이션을 만든 것
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Test
@Tag("fast")
public @interface FastTest {
}
