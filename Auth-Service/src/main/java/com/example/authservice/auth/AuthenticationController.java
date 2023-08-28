package com.example.authservice.auth;

import com.example.authservice.reponse.WrapResponse;
import com.example.authservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    public WrapResponse register(
           @Valid @RequestBody RegisterRequest request
    ) {
        return WrapResponse.ok(authenticationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
    @GetMapping("/validate-token")
    public ResponseEntity<?> validToken(@RequestParam(name = "token") String Token){
        if(authenticationService.validateToken(Token)){
            return ResponseEntity.ok("Valid Token");
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Token");
        }
    }
}
