package com.blogapplication.services.Impl;

import com.blogapplication.entity.User;
import com.blogapplication.exceptions.ResourceNotFoundException;
import com.blogapplication.paylods.UserDto;
import com.blogapplication.repository.UserRepo;
import com.blogapplication.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;


    // using dto's for implementation service class
    @Override
    public UserDto createuser(UserDto userdto) {
        // here i am using DTO first converting DTO to user then after saving again i am
        // converting user to user DTO
        User user = this.dtoToUser(userdto);
        User savedUser = this.userRepo.save(user);
        return this.usertoDto(savedUser);
    }

    //Update the User
    @Override
    public UserDto updateuser(UserDto userdto, Integer userId) {
        // if user not exits it will give proper exception
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        user.setName(userdto.getName());
        user.setEmail(userdto.getEmail());
        user.setPassword(userdto.getPassword());
        user.setAbout(userdto.getAbout());

        User updateduser = this.userRepo.save(user);
        // UserDto usertoDto1 = this.usertoDto(updateduser);
        // return usertoDto1 ;
        return this.usertoDto(updateduser);

    }

    //List User by user id
    @Override
    public UserDto getuserById(Integer userId) {
        //
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        UserDto usertoDto = this.usertoDto(user);
        return usertoDto;

    }


    //List all user
    @Override
    public List<UserDto> getAllusers() {
        List<User> users = this.userRepo.findAll();
        // using lamda's and stream method for converting list of user to list of user
        // dto's
        List<UserDto> userdtos = users.stream().map(user -> this.usertoDto(user)).collect(Collectors.toList());
        return userdtos;

    }

    //Delete the User
    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);

    }


    // we will model mapper libraray to make DTO using model mapper
//by the way we do not need to create method like that we can use directly also using modelmapper object

    //basically we returning in user so we need to convert dto's to main entites
    public User dtoToUser(UserDto userdto) {
        User user=this.modelMapper.map(userdto, User.class);

        /*
         * user.setId(userdto.getId()); user.setName(userdto.getName());
         * user.setEmail(userdto.getEmail()); user.setAbout(userdto.getAbout());
         * user.setPassword(userdto.getPassword());
         */

        return user;
    }


    //method to convert USER ENTITY to USER DTO
    public UserDto usertoDto(User user) {
        UserDto userdto =this.modelMapper.map(user, UserDto.class);

        return userdto;
    }

}
