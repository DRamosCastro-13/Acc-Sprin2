package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.TaskStatus;

public record NewTaskDTO(String title, String description, TaskStatus status) {
}
