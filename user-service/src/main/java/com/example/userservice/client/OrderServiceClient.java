package com.example.userservice.client;

import com.example.userservice.vo.response.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient (name = "order-service") // 호출하고자 하는 Micro Service 의 이름을 적는다.
public interface OrderServiceClient {

    @GetMapping ("/order-service/{userId}/orders") // Order-Service 에 있는 endpoint
//    @GetMapping ("/order-service/{userId}/orders_ng") // 예외처리 테스트를 위한 Endpoint 를 일부러 잘못 입력
    List<ResponseOrder> getOrders(@PathVariable final String userId);

    @PostMapping ("/order-service/{userId}/orders")
    ResponseOrder createOrders (@PathVariable final String userId);

    @DeleteMapping ("/order-service/{userId}/orders/{orderId}")
    void deleteOrders (@PathVariable final String userId,
                       @PathVariable final String orderId);
}
