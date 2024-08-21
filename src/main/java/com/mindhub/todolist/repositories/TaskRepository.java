package com.mindhub.todolist.repositories;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findById(Long aLong);

    Task findByUserEntityAndId(UserEntity userEntity, Long id);

}
