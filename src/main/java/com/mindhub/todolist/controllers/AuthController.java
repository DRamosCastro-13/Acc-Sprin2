package com.mindhub.todolist.controllers;

import com.mindhub.todolist.configurationsJWT.JwtUtils;
import com.mindhub.todolist.dtos.LoginUser;
import com.mindhub.todolist.dtos.NewUserEntityDTO;
import com.mindhub.todolist.models.RoleType;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginUser loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateClaims(authentication.getName());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping
    public ResponseEntity<?> createUserEntity(
            @RequestBody NewUserEntityDTO newUserEntity
    ){
        if(userEntityService.existsByEmail(newUserEntity.email())){
            return new ResponseEntity<>("This email is already in use", HttpStatus.FORBIDDEN);
        }

        UserEntity userEntity = new UserEntity(newUserEntity.username(), passwordEncoder.encode(newUserEntity.password()),
                newUserEntity.email());
        userEntity.setRole(RoleType.USER);
        userEntityService.saveUserEntity(userEntity);

        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }

}

