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
    public ResponseEntity<?> getUserEntityById(
            Authentication authentication,
            @PathVariable Long id){
        UserEntity userEntity = userEntityService.getAuthenticatedUser(authentication.getName());

        if(userEntity.getRole() != RoleType.ADMIN){
            return new ResponseEntity<>("You don't have permission to complete this request",HttpStatus.FORBIDDEN);
        }

        UserEntityDTO userEntityDTO = new UserEntityDTO(userEntity);
        return ResponseEntity.ok(userEntityDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(Authentication authentication){
        UserEntity admin = userEntityService.getAuthenticatedUser(authentication.getName());
        if(admin.getRole() != RoleType.ADMIN){
            return new ResponseEntity<>("You don't have permission to complete this request", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(userEntityService.getAllUsersDTO(), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id,
            Authentication authentication
            ){
        UserEntity admin = userEntityService.getAuthenticatedUser(authentication.getName());

        if (admin == null || admin.getRole() != RoleType.ADMIN) {
            return new ResponseEntity<>("You don't have permission to complete this request", HttpStatus.FORBIDDEN);
        }

        UserEntity userToDelete = userEntityService.findById(id);

        if (userToDelete == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        userEntityService.deleteUserEntity(userToDelete);

        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}
