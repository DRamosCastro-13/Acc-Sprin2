package com.mindhub.todolist.services;

import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.models.UserEntity;

import java.util.List;

public interface UserEntityService {

    UserEntity findById(Long id);

    boolean existsByEmail(String email);

    void saveUserEntity(UserEntity userEntity);

    List<UserEntityDTO> getAllUsersDTO();

    List<UserEntity> getAllUsers();

    UserEntity getAuthenticatedUser(String email);

    UserEntityDTO getAuthenticatedUserDTO(String email);

}
