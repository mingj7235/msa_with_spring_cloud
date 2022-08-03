package com.example.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient (name = "catalog-service")
public interface CatalogServiceClient {

    @GetMapping ("/catalog-service/{userId}")
    List<String> getCatalogNameList (@PathVariable String userId);
}
