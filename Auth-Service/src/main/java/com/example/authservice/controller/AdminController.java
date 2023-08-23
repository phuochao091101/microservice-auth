package com.example.authservice.controller;


import com.example.authservice.common.WrapResponseStatus;
import com.example.authservice.reponse.WrapResponse;
import com.example.authservice.request.CreateUserRequest;
import com.example.authservice.request.UpdateUserRequest;
import com.example.authservice.service.UserService;
import com.example.authservice.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "admin")
public class AdminController {
    @Autowired
    private UserService userService;
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
    @GetMapping()
    @PreAuthorize("hasAuthority('admin:read')")
    public WrapResponse getListUser(){
        List<User> listUser = userService.findAll();
        return WrapResponse.ok(listUser);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public WrapResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        userService.save(request);
        String message="Create Success";
        return WrapResponse.ok(message);
    }
    @PutMapping()
    @PreAuthorize("hasAuthority('admin:update')")
    public WrapResponse updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest)
    {
        userService.update(updateUserRequest);
        String message="Update Success";
        return WrapResponse.ok(message);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public WrapResponse delete(@PathVariable("id") String id) {
        userService.deleteById(Integer.parseInt(id));
        String message="Delete Success";
        return WrapResponse.ok(message);
    }
}
