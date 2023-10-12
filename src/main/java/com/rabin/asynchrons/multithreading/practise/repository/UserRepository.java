package com.rabin.asynchrons.multithreading.practise.repository;

import com.rabin.asynchrons.multithreading.practise.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
