package com.example.authservice.service.Impl;

import com.example.authservice.entity.Task;
import com.example.authservice.exception.NotFoundException;
import com.example.authservice.exception.ServiceException;
import com.example.authservice.repository.TaskRepository;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.request.CreateTaskRequest;
import com.example.authservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public List<Task> findTasksByUser_Id(Integer id) {
        if(!userRepository.findById(id).isPresent()){
            throw new NotFoundException("Invalid UserId");
        }
        if(taskRepository.findTasksByUser_Id(id).size()==0){
            throw new ServiceException("None Task");
        }
        return taskRepository.findTasksByUser_Id(id);
    }

    @Override
    public void deleteById(Integer id) {
        if(!userRepository.findById(id).isPresent()){
            throw new NotFoundException("Invalid UserId");
        }
        taskRepository.deleteById(id);
    }

    @Override
    public void save(CreateTaskRequest request) {
        var user = Task.builder()
                .content(request.getContent())
                .user(userRepository.findById(Integer.parseInt(request.getUserId())).get())
                .build();
        taskRepository.save(user);
    }
}
