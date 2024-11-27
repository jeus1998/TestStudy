package com.example.thejavatest.repeat;

import com.example.thejavatest.domain.Study;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepeatTest {
    @DisplayName("반복 테스트 연습")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo){
        System.out.println("test "
                + repetitionInfo.getCurrentRepetition()
                + "/" + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("ParameterizedTest 연습")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요"})
    @NullSource
    @EmptySource
    void parameterizedTest(String message){
        System.out.println(message);
    }
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void test(@ConvertWith(StudyConverter.class) Study study){
        // System.out.println(study.getLimit());
    }
    static class StudyConverter extends SimpleArgumentConverter{
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, () -> "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }
    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바'", "20, '스프링'"})
    void cscSource(Integer limit, String name){
        System.out.println(new Study(limit, name));
    }
    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바'", "20, '스프링'"})
    void cscSource2(ArgumentsAccessor argumentsAccessor){
        System.out.println(
                new Study(argumentsAccessor.getInteger(0),
                          argumentsAccessor.getString(1)));
    }
    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바'", "20, '스프링'"})
    void cscSource3(@AggregateWith(StudyAggregator.class) Study study){
        System.out.println(study);
    }
    static class StudyAggregator implements ArgumentsAggregator{
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }
}
