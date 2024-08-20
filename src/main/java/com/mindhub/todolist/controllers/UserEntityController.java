package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.NewUserEntityDTO;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.models.RoleType;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/userEntity")
public class UserEntityController {
    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public ResponseEntity<UserEntityDTO> getUserEntityById(@PathVariable Long id){
        UserEntity userEntity = userEntityService.findById(id);
        UserEntityDTO userEntityDTO = new UserEntityDTO(userEntity);
        return ResponseEntity.ok(userEntityDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(Authentication authentication){
        UserEntity admin = userEntityService.getAuthenticatedUser(authentication.getName());
        if(admin.getRole() != RoleType.ADMIN){
            return new ResponseEntity<>("You don't have permission to complete this request",HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(userEntityService.getAllUsersDTO(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUserEntity(
            @RequestBody NewUserEntityDTO newUserEntity
            ){
        if(userEntityService.existsByEmail(newUserEntity.email())){
            return new ResponseEntity<>("This email is already in use", HttpStatus.FORBIDDEN);
        }

        UserEntity userEntity = new UserEntity(newUserEntity.username(), newUserEntity.password(), newUserEntity.email());

        userEntityService.saveUserEntity(userEntity);

        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }
}
