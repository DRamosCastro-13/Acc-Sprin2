package com.mindhub.todolist.services.impl;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task findByUserEntityAndId(UserEntity userEntity, Long id) {
        return taskRepository.findByUserEntityAndId(userEntity, id);
    }

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

}
