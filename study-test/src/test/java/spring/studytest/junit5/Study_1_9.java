package spring.studytest.junit5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;
import spring.studytest.Study;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit5 테스트 반복하기
 */
public class Study_1_9 {

    @DisplayName("반복 테스트 연습1")
    @RepeatedTest(value = 10, name = "{currentRepetition}/{totalRepetitions} {displayName}")
    void repeatedTest(RepetitionInfo repetitionInfo) {
        System.out.println(
        "test " + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("반복 테스트 연습2")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요"})
    void repeatedTest2(String message) {
        System.out.println(message);
    }

    @DisplayName("반복 테스트 연습3")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요"})
    @NullAndEmptySource // @NullSource + @EmptySource
    void repeatedTest3(String message) {
        System.out.println(message);
    }

    @DisplayName("반복 테스트 연습4")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void repeatedTest4(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter{
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Cant only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("반복 테스트 연습5")
    @ParameterizedTest(name = "{index} {displayName} message={0} {1}")
    @CsvSource({"10, 자바 스터디", "20, 스프링"})
    void repeatedTest5(Integer limit, String name) {
        var study = new Study(limit, name);
        System.out.println(study);
    }

    @DisplayName("반복 테스트 연습6")
    @ParameterizedTest(name = "{index} {displayName} message={0} {1}")
    @CsvSource({"10, 자바 스터디", "20, 스프링"})
    void repeatedTest6(ArgumentsAccessor argumentsAccessor) {
        var study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        System.out.println(study);
    }

    @DisplayName("반복 테스트 연습7")
    @ParameterizedTest(name = "{index} {displayName} message={0} {1}")
    @CsvSource({"10, 자바 스터디", "20, 스프링"})
    void repeatedTest6(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator{
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
           return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }
}
