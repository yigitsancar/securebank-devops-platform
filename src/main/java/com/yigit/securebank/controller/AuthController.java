package com.yigit.securebank.controller;

import com.yigit.securebank.dto.AuthResponse;
import com.yigit.securebank.dto.LoginRequest;
import com.yigit.securebank.dto.RegisterRequest;
import com.yigit.securebank.dto.UserMapper;
import com.yigit.securebank.dto.UserResponse;
import com.yigit.securebank.entity.AppUser;
import com.yigit.securebank.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {
        AppUser user = userService.register(request);
        return UserMapper.toUserResponse(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        String token = userService.login(request);
        return new AuthResponse(token);
    }
}
