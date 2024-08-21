package com.mindhub.todolist.services;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;

public interface TaskService {

    Task findTaskById(Long id);

    void saveTask(Task task);

    Task findByUserEntityAndId(UserEntity userEntity, Long id);
}
