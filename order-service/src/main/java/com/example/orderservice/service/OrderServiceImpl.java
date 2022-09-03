package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.utils.CustomModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    private final CustomModelMapper modelMapper;

    @Override
    public OrderDto createOrder(final OrderDto orderDetails) {

        orderDetails.setOrderId(UUID.randomUUID().toString());
        orderDetails.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());

        OrderEntity orderEntity = modelMapper.standardMapper().map(orderDetails, OrderEntity.class);

        orderRepository.save(orderEntity);

        return modelMapper.standardMapper().map(orderEntity, OrderDto.class);
    }

    @Override
    public OrderDto getOrderByOrderId(final String orderId) {

        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);

        return new ModelMapper().map(orderEntity, OrderDto.class);
    }

    @Override
    public Iterable<OrderEntity> getAllOrdersByUserId(final String userId) {
        return orderRepository.findByUserId(userId);
    }

}
