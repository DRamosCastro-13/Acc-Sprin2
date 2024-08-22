package com.mindhub.todolist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.todolist.configurationsJWT.CustomUserDetailsService;
import com.mindhub.todolist.configurationsJWT.JwtUtils;
import com.mindhub.todolist.configurationsJWT.SecurityConfig;
import com.mindhub.todolist.controllers.AuthController;
import com.mindhub.todolist.controllers.TaskController;
import com.mindhub.todolist.dtos.NewTaskDTO;
import com.mindhub.todolist.models.TaskStatus;
import com.mindhub.todolist.models.UserEntity;
import com.mindhub.todolist.services.TaskService;
import com.mindhub.todolist.services.UserEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc
public class TaskControllerTest {
/*
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private AuthController authController;

    @Test
    @WithMockUser(username = "demo", authorities = {"ADMIN"})
    public void testCreateTask() throws Exception {
        NewTaskDTO newTaskDTO = new NewTaskDTO("Sample Title", "Description", TaskStatus.PENDING);
        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newTaskDTO)))
                .andExpect(status().isCreated());
    }*/
}