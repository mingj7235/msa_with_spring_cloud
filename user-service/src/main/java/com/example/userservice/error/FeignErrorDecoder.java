package com.example.userservice.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {

    private final Environment env;

    @Override
    public Exception decode(final String methodKey, final Response response) {

        switch (response.status()) {
            case 400 :
                break;
            case 404:
                if (methodKey.contains("getOrders")) { // Feign Client 인터페이스에 등록된 메소드 이름
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()),
                            env.getProperty("order_service.exception.order_is_empty"));
                }
            default:
                return new Exception(response.reason());
        }

        return null;
    }

}
