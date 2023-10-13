package com.rabin.asynchrons.multithreading.practise.service;

import com.rabin.asynchrons.multithreading.practise.dto.UserDto;
import com.rabin.asynchrons.multithreading.practise.entity.UserEntity;
import com.rabin.asynchrons.multithreading.practise.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Async
    @Override
    public CompletableFuture<List<UserDto>> saveAllTheRecord(MultipartFile file) throws IOException {
        long start=System.currentTimeMillis();

        List<UserDto> users=parseCSVFile(file); //from down
        log.info("saving list of users of size {}", users.size(), Thread.currentThread().getName());

        List<UserEntity> userEntityList = users.stream().map(userDto -> {
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(userDto, userEntity);
            return userEntity;
        }).collect(Collectors.toList());

//        List<UserEntity>userEntityList=new ArrayList<>();
//        for(UserDto userDot:users){
//            UserEntity userEntity=new UserEntity();
//            BeanUtils.copyProperties(userDot,userEntity);
//            userEntityList.add(userEntity);
//        }

        List<UserEntity> userss=userRepository.saveAll(userEntityList);
       // List<UserEntity> usersssss = CompletableFuture.supplyAsync(() -> userRepository.saveAll(userEntityList)).join();

        long end=System.currentTimeMillis();

        List<UserDto> userDtoList = userss.stream().map(userEntity -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            return userDto;
        }).collect(Collectors.toList());

//       List<UserDto> userDtoList=new ArrayList<>();
//       for(UserEntity userEntity:userss){
//           UserDto userDto=new UserDto();
//           BeanUtils.copyProperties(userEntity,userDto);
//           userDtoList.add(userDto);
//       }

        log.info("Total time {}", (end - start));

        return CompletableFuture.completedFuture(userDtoList);
    }

    @Async
    @Override
    public CompletableFuture<List<UserDto>> findAllRecord() {
        log.info("get list of user by "+Thread.currentThread().getName());
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = userEntityList.stream().map(userEntity -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            return userDto;
        }).collect(Collectors.toList());

//        List<UserDto>userDtoList=new ArrayList<>();
//        for(UserEntity userEntity:userEntityList){
//            UserDto userDto=new UserDto();
//            BeanUtils.copyProperties(userEntity,userDto);
//            userDtoList.add(userDto);
//        }
        return CompletableFuture.completedFuture(userDtoList);
    }

    private List<UserDto> parseCSVFile (final MultipartFile file) throws IOException {
       final List<UserDto> UserDtoList=new ArrayList<>();
        try{
            try(final BufferedReader br=new BufferedReader(new InputStreamReader(file.getInputStream()))){
                String line;
                while((line=br.readLine())!=null){
                    final String[] data=line.split(",");
                    if (data.length >= 6) {
                        final UserDto user = new UserDto();
                        user.setName(data[0]);
                        user.setEmail(data[1]);
                        user.setPassword(data[2]);
                        user.setAddress(data[3]);
                        user.setUsername(data[4]);
                        user.setGender(data[5]);
                        UserDtoList.add(user);
                    }else{
                        log.warn("Skipping line with insufficient data: " + line);
                    }
                }
                return UserDtoList;
            }
        }catch(final IOException e){
            log.error("Failed to parse CSV file {}", e);
            throw new IOException("Failed to parse CSV file {}", e);
        }
   }




}
