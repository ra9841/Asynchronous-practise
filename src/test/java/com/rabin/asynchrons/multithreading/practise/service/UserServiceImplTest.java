package com.rabin.asynchrons.multithreading.practise.service;

import com.rabin.asynchrons.multithreading.practise.dto.UserDto;
import com.rabin.asynchrons.multithreading.practise.entity.UserEntity;
import com.rabin.asynchrons.multithreading.practise.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private UserServiceImpl userServiceImplUnderTest;

    @Test
    void testSaveAllTheRecord() throws Exception {
        // Setup
        final MultipartFile file = new MockMultipartFile("name", "content".getBytes());

        // Configure UserRepository.saveAll(...).
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(0);
        userEntity.setName("name");
        userEntity.setEmail("email");
        userEntity.setPassword("password");
        userEntity.setAddress("address");
        final List<UserEntity> userEntityList = List.of(userEntity);
        final UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(0);
        userEntity1.setName("name");
        userEntity1.setEmail("email");
        userEntity1.setPassword("password");
        userEntity1.setAddress("address");
        final List<UserEntity> entities = List.of(userEntity1);
        when(mockUserRepository.saveAll(entities)).thenReturn(userEntityList);

        // Run the test
        final CompletableFuture<List<UserDto>> result = userServiceImplUnderTest.saveAllTheRecord(file);

        // Verify the results
    }

    @Test
    void testSaveAllTheRecord_UserRepositoryReturnsNoItems() throws Exception {
        // Setup
        final MultipartFile file = new MockMultipartFile("name", "content".getBytes());

        // Configure UserRepository.saveAll(...).
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(0);
        userEntity.setName("name");
        userEntity.setEmail("email");
        userEntity.setPassword("password");
        userEntity.setAddress("address");
        final List<UserEntity> entities = List.of(userEntity);
        when(mockUserRepository.saveAll(entities)).thenReturn(Collections.emptyList());

        // Run the test
        final CompletableFuture<List<UserDto>> result = userServiceImplUnderTest.saveAllTheRecord(file);

        // Verify the results
    }

    @Test
    void testFindAllRecord() {
        // Setup
        // Configure UserRepository.findAll(...).
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(0);
        userEntity.setName("name");
        userEntity.setEmail("email");
        userEntity.setPassword("password");
        userEntity.setAddress("address");
        final List<UserEntity> userEntityList = List.of(userEntity);
        when(mockUserRepository.findAll()).thenReturn(userEntityList);

        // Run the test
        final CompletableFuture<List<UserDto>> result = userServiceImplUnderTest.findAllRecord();

        // Verify the results
    }

    @Test
    void testFindAllRecord_UserRepositoryReturnsNoItems() {
        // Setup
        when(mockUserRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final CompletableFuture<List<UserDto>> result = userServiceImplUnderTest.findAllRecord();

        // Verify the results
    }
}
