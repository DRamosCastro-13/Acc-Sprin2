package com.mindhub.todolist.dtos;

import com.mindhub.todolist.models.UserEntity;

public class UserEntityDTO {

    private Long id;

    private String username, email;

    private Boolean active;

    public UserEntityDTO(UserEntity userEntity){
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.active = userEntity.getActive();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getActive(){return active;}
}
