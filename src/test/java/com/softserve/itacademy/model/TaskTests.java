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

public class TaskTests {

    private static Validator validator;
    private static Task validTask;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        validTask = new Task();
        validTask.setName("Prepare sprint report");
        validTask.setPriority(TaskPriority.MEDIUM);
        validTask.setTodo(new ToDo());
        validTask.setState(new State());
    }

    @Test
    void createValidTask() {
        Set<ConstraintViolation<Task>> violations = validator.validate(validTask);
        assertEquals(0, violations.size());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidNames")
    void constraintViolationInvalidName(String name, String expectedMessage) {
        Task task = new Task();
        task.setName(name);
        task.setPriority(TaskPriority.LOW);
        task.setTodo(new ToDo());
        task.setState(new State());

        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        assertEquals(1, violations.size());
        assertEquals(expectedMessage, violations.iterator().next().getMessage());
    }

    private static Stream<Arguments> provideInvalidNames() {
        return Stream.of(
                Arguments.of(null, "Task name cannot be empty"),
                Arguments.of("ab", "Task name must be between 3 and 200 characters"),
                Arguments.of("a".repeat(201), "Task name must be between 3 and 200 characters")
        );
    }

    @Test
    void constraintViolationMissingTodo() {
        Task task = new Task();
        task.setName("Valid task name");
        task.setPriority(TaskPriority.LOW);
        task.setTodo(null);
        task.setState(new State());

        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void constraintViolationMissingState() {
        Task task = new Task();
        task.setName("Valid task name");
        task.setPriority(TaskPriority.LOW);
        task.setTodo(new ToDo());
        task.setState(null);

        Set<ConstraintViolation<Task>> violations = validator.validate(task);

        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }
}