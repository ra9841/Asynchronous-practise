package com.rabin.asynchrons.multithreading.practise.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="USER_TBL")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String username;
    private String gender;

    public UserEntity(String name, String email, String password, String address, String username, String gender) {
    }
}
