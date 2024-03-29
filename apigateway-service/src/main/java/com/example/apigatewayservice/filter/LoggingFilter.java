package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(final Config config) {

//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            log.info("Global Filter baseMessage : {}", config.getBaseMessage());
//
//            if (config.isPreLogger()) {
//                log.info("Global Filter Start : request id -> {}", request.getId());
//            }
//
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//
//                if (config.isPostLogger()) {
//                    log.info("Global Filter End : response code -> {}", response.getStatusCode());
//                }
//
//            }));
//        };
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging Filter baseMessage : {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Logging PRE Filter: request id -> {}", request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                if (config.isPostLogger()) {
                    log.info("Logging POST Filter: response code -> {}", response.getStatusCode());
                }

            }));
//        }, Ordered.HIGHEST_PRECEDENCE); // Ordered.HIGHEST_PRECEDENCE 로 넣었기 때문에 Global Filter 보다도 먼저 실행된다. 즉, filter의 실행순서를 줄 수 있다.
        }, Ordered.LOWEST_PRECEDENCE); // Ordered.HIGHEST_PRECEDENCE 로 넣었기 때문에 Global Filter 보다도 먼저 실행된다. 즉, filter의 실행순서를 줄 수 있다.

        return filter;
    }

    @Data
    public static class Config {
        // Put the Configuration properties
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
