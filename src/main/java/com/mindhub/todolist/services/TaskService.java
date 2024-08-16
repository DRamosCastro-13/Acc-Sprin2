package com.mindhub.todolist.services;

import com.mindhub.todolist.models.Task;

public interface TaskService {

    Task findTaskById(Long id);

    void saveTask(Task task);
}
