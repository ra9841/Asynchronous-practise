package com.rabin.asynchrons.multithreading.practise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String username;
    private String gender;

    public UserDto(String name, String email, String password, String address, String username, String gender) {
    }
}
