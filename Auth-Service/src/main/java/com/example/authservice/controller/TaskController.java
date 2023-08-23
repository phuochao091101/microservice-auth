package com.example.authservice.controller;

import com.example.authservice.common.WrapResponseStatus;
import com.example.authservice.entity.Task;
import com.example.authservice.reponse.WrapResponse;
import com.example.authservice.request.CreateTaskRequest;
import com.example.authservice.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/task")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Task")
public class TaskController {

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
    @PreAuthorize("hasAuthority('admin:read')")
    public WrapResponse getListUser(@PathVariable String id) {
        List<Task> listTask = taskService.findTasksByUser_Id(Integer.parseInt(id));
        return WrapResponse.ok(listTask);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public WrapResponse createUser(@Valid @RequestBody CreateTaskRequest request) {
        taskService.save(request);
        String message="Create Success";
        return WrapResponse.ok(message);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public WrapResponse delete(@PathVariable("id") String id) {
        taskService.deleteById(Integer.parseInt(id));
        String message="Delete Success";
        return WrapResponse.ok(message);
    }
}
