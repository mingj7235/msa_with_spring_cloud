package com.example.userservice.client;

import com.example.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient (name = "order-service") // 호출하고자 하는 Micro Service 의 이름을 적는다.
public interface OrderServiceClient {

    @GetMapping ("/order-service/{userId}/orders") // Order-Service 에 있는 endpoint
    List<ResponseOrder> getOrders(@PathVariable final String userId);
}
