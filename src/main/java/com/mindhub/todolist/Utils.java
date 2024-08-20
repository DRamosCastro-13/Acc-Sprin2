package com.mindhub.todolist;

import com.mindhub.todolist.models.RoleType;
import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.TaskStatus;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //porque adentro tendrá Beans
public class Utils {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData(UserEntityRepository userEntityRepository, TaskRepository taskRepository){
        return args -> {

            UserEntity user = new UserEntity("jane_doe", passwordEncoder.encode("1234"), "janedoe@gmail.com");
            user.setRole(RoleType.USER);
            userEntityRepository.save(user);

            Task task1 = new Task("Finish Sprint", "Work on current srpint", TaskStatus.PENDING);
            user.addTask(task1);
            taskRepository.save(task1);

            UserEntity admin = new UserEntity("admin", passwordEncoder.encode("12345"), "admin@admin.com");
            admin.setRole(RoleType.ADMIN);

            userEntityRepository.save(admin);

        };
    }
}
