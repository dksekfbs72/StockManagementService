package com.stockmanagementservice.global.config.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stock", url = "http://localhost:8081/activity/product/stock")
public interface ProductClient {
    @GetMapping
    Long getStock(@RequestParam Long productId);
}
