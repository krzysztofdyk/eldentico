package com.example.eldentico.user.controller;

import com.example.eldentico.user.dto.UserRequestDto;
import com.example.eldentico.user.dto.UserResponseDto;
import com.example.eldentico.user.entity.UserEntity;
import com.example.eldentico.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    //@Operation(summary = "Get users.")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> findAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping ("/users/page")
    //@Operation(summary = "Get users.")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserResponseDto> findAllUsersPaged(Pageable pageable){
        return userService.findAllUsersPaged(pageable);
    }

    @GetMapping ("/users/transfers")
    //@Operation(summary = "Get users and transfers.")
    @ResponseStatus(HttpStatus.OK)
    public List<UserEntity> findUsersAndTransfers(){
        return userService.findUsersAndTransfers();
    }

    @PostMapping("/users")
    //@Operation(summary = "Create user.")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus createUser(@RequestBody UserRequestDto userRequestDto){
        UserEntity userEntity = userService.createUser(userRequestDto);
        //return userService.mapDto(userEntity);
        return HttpStatus.CREATED;
    }

    @PutMapping("/users/{id}")
    //@Operation(summary = "Update user.")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserResponseDto updateUser(@PathVariable Long id ,@RequestBody UserRequestDto userRequestDto){
        UserEntity userEntity = userService.updateUser(id,userRequestDto);
        return userService.mapEntityToDto(userEntity);
    }

    @DeleteMapping("/users/{id}")
    //@Operation(summary = "Delete user.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpStatus deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return HttpStatus.NO_CONTENT;
    }

}
