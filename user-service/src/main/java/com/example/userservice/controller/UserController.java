package com.example.userservice.controller;

import com.example.userservice.config.CustomModelMapperConfig;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.serivce.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping ("/")
@RequiredArgsConstructor
public class UserController {

    private final Environment env;

    private final Greeting greeting;

    private final UserService userService;

    private final CustomModelMapperConfig customMapper;

    @GetMapping ("/health_check")
    public String status () {
        return "It's Working in User Service "
                + ", port (local.server.port) : " + env.getProperty("local.server.port")
                + ", port (server.port) : " + env.getProperty("server.port")
                + ", token secret : " + env.getProperty("token.secret")
                + ", token expiration time : " + env.getProperty("token.expiration_time")
        ;
    }

    @GetMapping ("/welcome")
    public String welcome () {
        return greeting.getMessage();
    }

    @PostMapping ("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser requestUser) {

        UserDto userDto = customMapper.strictMapper().map(requestUser, UserDto.class);
        userService.createUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customMapper.strictMapper().map(userDto, ResponseUser.class));
    }

    @GetMapping ("/users")
    public ResponseEntity<List<ResponseUser>> getUsers () {

        Iterable<UserEntity> userList = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();

        userList.forEach( r -> {
            result.add(new ModelMapper().map(r, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping ("/users/{userId}")
    public ResponseEntity<ResponseUser> getUserById (@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(customMapper.strictMapper().map(userDto, ResponseUser.class));
    }
}
