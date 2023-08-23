package com.example.authservice.auth;

import com.example.authservice.common.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Content is mandatory")
    private String firstname;
    @NotBlank(message = "Content is mandatory")
    private String lastname;
    @Email(message = "Invalid email")
    private String email;
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    private Role role;
}
