package com.example.taskmanager;

public class Task {
    private long id;
    private String description;
    private String created_at;
    private String complete_time;
    private boolean is_completed;

    public Task(long id, String description, String created_at, String complete_time, boolean is_completed) {
        this.id = id;
        this.description = description;
        this.created_at = created_at;
        this.complete_time = complete_time;
        this.is_completed = is_completed;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getComplete_time() {
        return complete_time;
    }

    public boolean getIs_completed() {
        return is_completed;
    }
}

