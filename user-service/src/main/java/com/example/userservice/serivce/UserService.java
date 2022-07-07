package com.example.userservice.serivce;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId (String userId);

    Iterable<UserEntity> getUserByAll ();
}
