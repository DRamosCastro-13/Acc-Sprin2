package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.NewTaskDTO;
import com.mindhub.todolist.dtos.NewUserEntityDTO;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.services.TaskService;
import com.mindhub.todolist.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserEntityService userEntityService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id){ //del task
        Task task = taskService.findTaskById(id);
        TaskDTO taskDTO = new TaskDTO(task);
        return ResponseEntity.ok(taskDTO);
    }

    @PostMapping
    public ResponseEntity<?> createTaskByUserId(
            Authentication authentication,
            @RequestBody NewTaskDTO newTaskDTO
            ){
        UserEntity user = userEntityService.getAuthenticatedUser(authentication.getName());

        if(user.getId() == null){
            return new ResponseEntity<>("User id not found", HttpStatus.NOT_FOUND);
        }

        Task task = new Task(newTaskDTO.title(), newTaskDTO.description(), newTaskDTO.status());
        user.addTask(task);

        taskService.saveTask(task);

        return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
    }
}
