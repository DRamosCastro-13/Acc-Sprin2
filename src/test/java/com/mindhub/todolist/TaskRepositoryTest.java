package com.mindhub.todolist;

import com.mindhub.todolist.models.Task;
import com.mindhub.todolist.models.TaskStatus;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.repositories.TaskRepository;
import com.mindhub.todolist.repositories.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {
/*
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Test
    public void testFindById(){
        Task task = new Task("Sample Task", "This is a sample task", TaskStatus.PENDING);
        taskRepository.save(task);

        Optional<Task> foundTask = taskRepository.findById(task.getId());

        assertThat(foundTask, is(notNullValue()));
        assertThat(foundTask.get().getTitle(), is(equalTo(task.getTitle())));
        assertThat(foundTask.get().getDescription(), is(equalTo(task.getDescription())));
        assertThat(foundTask.get().getStatus(), is(equalTo(task.getStatus())));
    }

    @Test
    public void testFindByUserEntityAndId(){

        UserEntity user = new UserEntity("testuser", "password", "testuser@example.com");
        userEntityRepository.save(user);

        Task task = new Task("Sample Task", "This is a sample task", TaskStatus.PENDING);
        user.addTask(task);
        taskRepository.save(task);


        Task foundTask = taskRepository.findByUserEntityAndId(user, task.getId());


        assertThat(foundTask, is(equalTo(task)));
        assertThat(foundTask, is(notNullValue()));
    }*/
}
