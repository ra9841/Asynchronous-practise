package com.rabin.asynchrons.multithreading.practise.controller;

import com.rabin.asynchrons.multithreading.practise.dto.UserDto;
import com.rabin.asynchrons.multithreading.practise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/multi")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value="/users",consumes={MediaType.MULTIPART_FORM_DATA_VALUE},produces = "application/json")
    public ResponseEntity<CompletableFuture<List<UserDto>>> saveUsers(@RequestParam(value="files")MultipartFile[] files) throws IOException {
        for(MultipartFile file:files){
            userService.saveAllTheRecord(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/users",produces = "application/json")
    public CompletableFuture<List<UserDto>> findAllUsers(){
        return userService.findAllRecord();
    }

    @GetMapping(value = "/getUserByThread",produces = "application/json")
    public ResponseEntity<CompletableFuture<List<UserDto>>> getUser(){
        //this is for multithreading purpose, you can add any number of this to check how many thread pool worked
        CompletableFuture<List<UserDto>> users1=userService.findAllRecord();
        CompletableFuture<List<UserDto>> users2=userService.findAllRecord();
        CompletableFuture<List<UserDto>> users3=userService.findAllRecord();
        CompletableFuture<List<UserDto>> users4=userService.findAllRecord();
        CompletableFuture<List<UserDto>> users5=userService.findAllRecord();
        CompletableFuture<List<UserDto>> users6=userService.findAllRecord();
        CompletableFuture.allOf(users1,users2,users3,users4,users5,users6).join();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
