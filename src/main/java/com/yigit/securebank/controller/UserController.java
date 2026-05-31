package com.yigit.securebank.controller;

import com.yigit.securebank.dto.UserResponse;
import com.yigit.securebank.entity.AppUser;
import com.yigit.securebank.repository.AppUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final AppUserRepository userRepository;

    public UserController(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public UserResponse me(Authentication authentication) {

        String username = authentication.getName();

        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getBalance()
        );
    }
}
