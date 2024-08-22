package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.NewUserEntityDTO;
import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.models.RoleType;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.services.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get a user entity by ID", description = "Retrieves a user entity by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User entity found"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid user ID"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden, user does not have admin privileges")
    })
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
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get all user entities", description = "Retrieves a list of all user entities.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of user entities retrieved"),
            @ApiResponse(responseCode = "403", description = "Forbidden, user does not have admin privileges")
    })
    public ResponseEntity<?> getAllUsers(Authentication authentication){
        UserEntity admin = userEntityService.getAuthenticatedUser(authentication.getName());
        if(admin.getRole() != RoleType.ADMIN){
            return new ResponseEntity<>("You don't have permission to complete this request", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(userEntityService.getAllUsersDTO(), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a user", description = "Deletes a user by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User entity deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid user ID"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden, user does not have admin privileges")
    })
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
