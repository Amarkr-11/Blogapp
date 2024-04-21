package com.blogapplication.services;

import com.blogapplication.paylods.UserDto;

import java.util.List;

public interface UserService {
    UserDto createuser(UserDto user);
    UserDto updateuser(UserDto user, Integer userId);
    UserDto getuserById(Integer userId);
    List<UserDto> getAllusers();
    void deleteUser(Integer userId);
}
