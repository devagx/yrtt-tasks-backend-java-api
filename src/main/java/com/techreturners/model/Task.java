package com.techreturners.model;

public class Task {

    private String taskId;
    private String description;
    private String userId;
    private boolean completed;

    public Task() {}

    public Task(String taskId, String description) {
        this.taskId = taskId;
        this.description = description;
        this.completed = false;
    }

    public Task(String taskId, String description, boolean completed, String userId) {
        this.taskId = taskId;
        this.description = description;
        this.completed = completed;
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }
}