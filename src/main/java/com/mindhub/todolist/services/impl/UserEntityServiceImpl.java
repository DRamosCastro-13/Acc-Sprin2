package com.mindhub.todolist.services.impl;

import com.mindhub.todolist.dtos.UserEntityDTO;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.UserEntityRepository;
import com.mindhub.todolist.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public UserEntity findById(Long id) {
        return userEntityRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userEntityRepository.existsByEmail(email);
    }

    @Override
    public void saveUserEntity(UserEntity userEntity) {
        userEntityRepository.save(userEntity);
    }

    @Override
    public List<UserEntityDTO> getAllUsersDTO() {
        return getAllUsers().stream().map(userEntity -> new UserEntityDTO(userEntity)).collect(Collectors.toList());
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userEntityRepository.findAll();
    }

    @Override
    public UserEntity getAuthenticatedUser(String email) {
        return userEntityRepository.findByEmail(email).orElse(null);
    }

    @Override
    public UserEntityDTO getAuthenticatedUserDTO(String email) {
        return new UserEntityDTO(getAuthenticatedUser(email));
    }

    @Override
    public void deleteUserEntity(UserEntity userEntity) {
        userEntityRepository.delete(userEntity);
    }


}
