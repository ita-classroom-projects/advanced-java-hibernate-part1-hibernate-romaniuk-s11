package com.softserve.itacademy.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StateTests {

    private static Validator validator;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void createValidState() {
        State state = new State();
        state.setName("In-Progress");

        Set<ConstraintViolation<State>> violations = validator.validate(state);
        assertEquals(0, violations.size());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidNames")
    void constraintViolationInvalidName(String name, String expectedMessage) {
        State state = new State();
        state.setName(name);

        Set<ConstraintViolation<State>> violations = validator.validate(state);

        assertEquals(1, violations.size());
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    private static Stream<Arguments> provideInvalidNames() {
        return Stream.of(
                Arguments.of(null, "State name cannot be empty"),
                Arguments.of("ThisStateNameIsTooLong", "State name must be between 1 and 20 characters"),
                Arguments.of("Inva!id", "State name can only contain Latin letters, numbers, dashes, spaces, and underscores")
        );
    }
}