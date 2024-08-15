package com.mindhub.todolist;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.TaskStatus;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.repositories.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //porque adentro tendrá Beans
public class Utils {

    @Bean
    public CommandLineRunner initData(UserEntityRepository userEntityRepository, TaskRepository taskRepository){
        return args -> {

            UserEntity user = new UserEntity("jane_doe", "1234", "janedoe@gmail.com");
            userEntityRepository.save(user);

            Task task1 = new Task("Finish Sprint", "Work on current srpint", TaskStatus.PENDING);
            user.addTask(task1);
            taskRepository.save(task1);

        };
    }
}
