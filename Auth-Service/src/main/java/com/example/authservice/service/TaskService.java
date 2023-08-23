package com.example.authservice.service;

import com.example.authservice.entity.Task;
import com.example.authservice.request.CreateTaskRequest;

import java.util.List;

public interface TaskService {
    List<Task> findTasksByUser_Id(Integer id);
    void deleteById(Integer id);
    void save(CreateTaskRequest createTaskRequest);
}
