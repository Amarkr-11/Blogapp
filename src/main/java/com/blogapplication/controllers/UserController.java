package com.blogapplication.controllers;

import com.blogapplication.paylods.ApiResponse;
import com.blogapplication.paylods.UserDto;
import com.blogapplication.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    //Create a user
    @PostMapping("/create")
    public ResponseEntity<UserDto> CreateUser(@Valid @RequestBody UserDto userdto){
        UserDto createUserDto = this.userService.createuser(userdto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);

    }

    //Update a user
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> UpdateUser(@Valid @RequestBody UserDto userdto, @PathVariable("userId") Integer userId){
        UserDto updateuser = this.userService.updateuser(userdto, userId);
        return ResponseEntity.ok(updateuser);

    }

    //Delete the user
    @DeleteMapping("delete/{userId}")
    public ResponseEntity<ApiResponse> DeleteUser(@PathVariable("userId") Integer userId){
        this.userService.deleteUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully !!",true),HttpStatus.OK);
    }

    //List of users

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> GetAllUser(){
        return ResponseEntity.ok(this.userService.getAllusers());
    }

//    //List of one user

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDto> GetOneUser(@PathVariable("userId") Integer userId){
        return ResponseEntity.ok(this.userService.getuserById(userId));
    }

}
