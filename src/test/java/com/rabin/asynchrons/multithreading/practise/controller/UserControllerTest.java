package com.rabin.asynchrons.multithreading.practise.controller;

import com.rabin.asynchrons.multithreading.practise.dto.UserDto;
import com.rabin.asynchrons.multithreading.practise.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;

    @Test
    void testSaveUsers() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(multipart("/multi/users")
                        .file(new MockMultipartFile("files", "originalFilename", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockUserService).saveAllTheRecord(any(MultipartFile.class));
    }

    @Test
    void testSaveUsers_UserServiceThrowsIOException() throws Exception {
        // Setup
        when(mockUserService.saveAllTheRecord(any(MultipartFile.class))).thenThrow(IOException.class);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(multipart("/multi/users")
                        .file(new MockMultipartFile("files", "originalFilename", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testFindAllUsers() throws Exception {
        // Setup
        // Configure UserService.findAllRecord(...).
        final UserDto userDto = new UserDto();
        userDto.setId(0);
        userDto.setName("name");
        userDto.setEmail("email");
        userDto.setPassword("password");
        userDto.setAddress("address");
        final CompletableFuture<List<UserDto>> listCompletableFuture = CompletableFuture.completedFuture(
                List.of(userDto));
        when(mockUserService.findAllRecord()).thenReturn(listCompletableFuture);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/multi/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testFindAllUsers_UserServiceReturnsNoItems() throws Exception {
        // Setup
        // Configure UserService.findAllRecord(...).
        final CompletableFuture<List<UserDto>> listCompletableFuture = CompletableFuture.completedFuture(
                Collections.emptyList());
        when(mockUserService.findAllRecord()).thenReturn(listCompletableFuture);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/multi/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testFindAllUsers_UserServiceReturnsFailure() throws Exception {
        // Setup
        // Configure UserService.findAllRecord(...).
        final CompletableFuture<List<UserDto>> listCompletableFuture = CompletableFuture.failedFuture(
                new Exception("message"));
        when(mockUserService.findAllRecord()).thenReturn(listCompletableFuture);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/multi/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetUser() throws Exception {
        // Setup
        // Configure UserService.findAllRecord(...).
        final UserDto userDto = new UserDto();
        userDto.setId(0);
        userDto.setName("name");
        userDto.setEmail("email");
        userDto.setPassword("password");
        userDto.setAddress("address");
        final CompletableFuture<List<UserDto>> listCompletableFuture = CompletableFuture.completedFuture(
                List.of(userDto));
        when(mockUserService.findAllRecord()).thenReturn(listCompletableFuture);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/multi/getUserByThread")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetUser_UserServiceReturnsNoItems() throws Exception {
        // Setup
        // Configure UserService.findAllRecord(...).
        final CompletableFuture<List<UserDto>> listCompletableFuture = CompletableFuture.completedFuture(
                Collections.emptyList());
        when(mockUserService.findAllRecord()).thenReturn(listCompletableFuture);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/multi/getUserByThread")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetUser_UserServiceReturnsFailure() throws Exception {
        // Setup
        // Configure UserService.findAllRecord(...).
        final CompletableFuture<List<UserDto>> listCompletableFuture = CompletableFuture.failedFuture(
                new Exception("message"));
        when(mockUserService.findAllRecord()).thenReturn(listCompletableFuture);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/multi/getUserByThread")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}
