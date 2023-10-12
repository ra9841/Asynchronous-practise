package com.rabin.asynchrons.multithreading.practise.service;

import com.rabin.asynchrons.multithreading.practise.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {

    CompletableFuture<List<UserDto>> saveAllTheRecord(MultipartFile file) throws IOException;

    CompletableFuture<List<UserDto>> findAllRecord();
}
