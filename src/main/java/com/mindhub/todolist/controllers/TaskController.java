package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.NewTaskDTO;
import com.mindhub.todolist.dtos.NewUserEntityDTO;
import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.TaskStatus;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.services.TaskService;
import com.mindhub.todolist.services.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get a task by ID", description = "Retrieves a task belonging to the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden, user does not own the task")
    })
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
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a new task", description = "Creates a new task for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "403", description
                    = "Bad request, invalid task data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> createTask(
            Authentication authentication,
            @RequestBody NewTaskDTO newTaskDTO
            ){
        UserEntity user = userEntityService.getAuthenticatedUser(authentication.getName());

        if(user == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if(newTaskDTO.title().isBlank()){
            return new ResponseEntity<>("Bad request, invalid task data", HttpStatus.FORBIDDEN);
        }

        if(newTaskDTO.description().isBlank()){
            return new ResponseEntity<>("Bad request, invalid task data", HttpStatus.FORBIDDEN);
        }

        if(newTaskDTO.status().toString().isBlank()){
            return new ResponseEntity<>("Bad request, invalid task data", HttpStatus.FORBIDDEN);
        }


        Task task = new Task(newTaskDTO.title(), newTaskDTO.description(), newTaskDTO.status());
        user.addTask(task);
        taskService.saveTask(task);

        return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
    }

    @PutMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update task status", description = "Updates the status of a task belonging to the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid task ID or status"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden, user does not own the task")
    })
    public ResponseEntity<?> updateTaskStatus(
            Authentication authentication,
            @RequestParam Long id,
            @RequestParam TaskStatus status
            ){
        UserEntity userEntity = userEntityService.getAuthenticatedUser(authentication.getName());

        if(userEntity == null){
            return new ResponseEntity<>("Unauthorized, authentication required", HttpStatus.UNAUTHORIZED);
        }

        Task task = taskService.findByUserEntityAndId(userEntity, id);

        if(task == null){
            return new ResponseEntity<>("Bad request, invalid task ID or status", HttpStatus.BAD_REQUEST);
        }

        if(status.toString().equals(task.getStatus().toString())){
            return new ResponseEntity<>("You need to enter a new status to update it", HttpStatus.BAD_REQUEST);
        }

        task.setStatus(status);
        taskService.saveTask(task);

        return new ResponseEntity<>("Task updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a task by ID", description = "Deletes a task belonging to the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid task ID"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden, user does not own the task")
    })
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
