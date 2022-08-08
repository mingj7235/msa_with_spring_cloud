package com.example.userservice.client;

import com.example.userservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotNull;
import java.util.List;

@FeignClient (name = "catalog-service")
public interface CatalogServiceClient {

    @GetMapping ("/catalog-service/{userId}")
    List<String> getCatalogNameList (@PathVariable String userId);

    @PostMapping ("/catalog-service/")
    String saveCatalogName (final @NotNull UserDto request);
}
