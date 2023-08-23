package com.example.authservice.controller;

import com.example.authservice.entity.Task;
import com.example.authservice.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User")
public class UserController {
    @Autowired
    private TaskService taskService;
    @Operation(
            description = "Get endpoint for manager",
            summary = "This is a summary for management get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getTasks(@PathVariable String id) {
        List<Task> listTask = taskService.findTasksByUser_Id(Integer.parseInt(id));
        if (listTask == null || listTask.isEmpty()) {
            String message = "No Task";
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(listTask);
    }
}
