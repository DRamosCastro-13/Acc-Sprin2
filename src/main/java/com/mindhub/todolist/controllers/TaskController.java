package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.NewTaskDTO;
import com.mindhub.todolist.dtos.NewUserEntityDTO;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.TaskStatus;
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
    public ResponseEntity<?> getTaskById(
            Authentication authentication,
            @PathVariable Long id){ //del task
        UserEntity user = userEntityService.getAuthenticatedUser(authentication.getName());

        if(user == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        Task task = taskService.findTaskById(id);

        if(task == null){
            return new ResponseEntity<>("Unable to locate task", HttpStatus.NOT_FOUND);
        }

        TaskDTO taskDTO = new TaskDTO(task);
        return ResponseEntity.ok(taskDTO);
    }

    @PostMapping
    public ResponseEntity<?> createTask(
            Authentication authentication,
            @RequestBody NewTaskDTO newTaskDTO
            ){
        UserEntity user = userEntityService.getAuthenticatedUser(authentication.getName());

        if(user == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if(newTaskDTO.title().isBlank()){
            return new ResponseEntity<>("Please add a title to create a new task", HttpStatus.FORBIDDEN);
        }

        if(newTaskDTO.description().isBlank()){
            return new ResponseEntity<>("Please add a description to create a new task", HttpStatus.FORBIDDEN);
        }

        if(newTaskDTO.status().toString().isBlank()){
            return new ResponseEntity<>("Please add a status to create a new task", HttpStatus.FORBIDDEN);
        }


        Task task = new Task(newTaskDTO.title(), newTaskDTO.description(), newTaskDTO.status());
        user.addTask(task);
        taskService.saveTask(task);

        return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateTaskStatus(
            Authentication authentication,
            @RequestParam Long id,
            @RequestParam TaskStatus status
            ){
        UserEntity userEntity = userEntityService.getAuthenticatedUser(authentication.getName());

        if(userEntity == null){
            return new ResponseEntity<>("Please login to complete request", HttpStatus.FORBIDDEN);
        }

        Task task = taskService.findByUserEntityAndId(userEntity, id);

        if(task == null){
            return new ResponseEntity<>("Unable to locate task", HttpStatus.FORBIDDEN);
        }

        if(status.toString().equals(task.getStatus().toString())){
            return new ResponseEntity<>("You need to enter a new status to update it", HttpStatus.FORBIDDEN);
        }

        task.setStatus(status);
        taskService.saveTask(task);

        return new ResponseEntity<>("Task updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(
            Authentication authentication,
            @PathVariable Long id
            ){
        UserEntity user = userEntityService.getAuthenticatedUser(authentication.getName());

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        Task task = taskService.findTaskById(id);

        if (task == null) {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        }

        taskService.deleteTask(task);
        return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
    }
}
