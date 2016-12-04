package com.example.chenx2.travelplanner.data;

public class Todo {
    public Todo(String todo, Boolean done) {
        this.todo = todo;
        this.done = done;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    private String todo;
    private Boolean done;
}
