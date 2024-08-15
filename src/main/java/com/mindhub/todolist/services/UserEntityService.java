package com.mindhub.todolist.services;

import com.mindhub.todolist.models.UserEntity;

public interface UserEntityService {

    UserEntity findById(Long id);

    boolean existsByEmail(String email);

    void saveUserEntity(UserEntity userEntity);

}
