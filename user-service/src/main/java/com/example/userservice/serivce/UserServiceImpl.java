package com.example.userservice.serivce;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.config.CustomModelMapperConfig;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.response.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    private final CustomModelMapperConfig customModelMapper;
    
//    private final RestTemplate restTemplate;

    private final OrderServiceClient orderServiceClient;
    
    private final Environment env;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail (username);

        if (userEntity == null)
            throw new UsernameNotFoundException(username);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }

    @Override
    public UserDto createUser(final UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        UserEntity userEntity = customModelMapper.strictMapper().map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(encoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        return customModelMapper.strictMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(final String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserDto userDto = customModelMapper.strictMapper().map(userEntity, UserDto.class);

        // Using as rest template ******
//        String orderUrl = String.format(env.getProperty("order_service.url"), userId);
//        ResponseEntity<List<ResponseOrder>> orderListResponse =
//                restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<ResponseOrder>>() {
//                });
//
//        List<ResponseOrder> ordersList = orderListResponse.getBody();

        // Using as Open Feign *********
        // Feign Exception Handling
        /**
         * FeignException 을 활용하면 자동적으로 FeignClient 로 받아온 정보를 제외하고 (예외가 난 곳)
         * 정상적으로 정보를 가져올 수 있는 것은 정상적으로 호출하여 통신한다.
         */
//        List<ResponseOrder> ordersList = null;
//        try {
//            ordersList = orderServiceClient.getOrders(userId);
//        } catch (FeignException ex) {
//            log.error(ex.getMessage());
//        }

        /**
         * ErrorDecoder를 사용하여 error 처리하였음
         */
        List<ResponseOrder> ordersList = orderServiceClient.getOrders(userId);

        userDto.setOrders(ordersList);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(final String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UsernameNotFoundException(email + "=> is not found");

        return customModelMapper.strictMapper().map(userEntity, UserDto.class);
    }

}
