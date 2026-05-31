package com.yigit.securebank.dto;

import com.yigit.securebank.entity.AppUser;

public class UserMapper {

    public static UserResponse toUserResponse(AppUser user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getBalance()
        );
    }
}
