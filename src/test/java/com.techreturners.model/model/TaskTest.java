package com.techreturners.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TaskTest {
    @Test
    @DisplayName("Test task description GET")
    public void testTaskDescription() {
        Task t = new Task("abc123", "Some task description", false);

        assertEquals("Some task description", t.getDescription(), "Task description was incorrect");
    }

    @Test
    @DisplayName("Check completed is false by default")
    public void testDefaultCompletedStatus() {
        Task t = new Task("abc1234", "Some description");

        assertFalse(t.isCompleted(), "Task status was not false by default");
    }

    @Test
    @DisplayName("Test task ID GET")
    public void testTaskId() {
        Task t = new Task("abc123", "Some task description", false);

        assertEquals("abc123", t.getTaskId(), "Task ID was incorrect");
    }

    @Test
    @DisplayName("Test task ID GET2")
    public void testTaskId2() {
        Task t = new Task("abc123", "Some task description", false);

        assertEquals("abc123", t.getTaskId(), "Task ID was incorrect");
    }
}