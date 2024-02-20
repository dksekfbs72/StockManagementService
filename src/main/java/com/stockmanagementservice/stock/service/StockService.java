package com.stockmanagementservice.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {
    private final RedisTemplate<String, Object> redisTemplate;
    public String addStock(Long productId, Long amount) {
        Object stock = redisTemplate.opsForValue().get(String.valueOf(productId));
        Long sumStockAndAmount = Long.parseLong(stock == null ? "0" : (String) stock) + amount;
        redisTemplate.opsForValue().set(String.valueOf(productId), String.valueOf(sumStockAndAmount));
        return "상품 수량 추가 성공";
    }

    public Long getStock(Long productId) {
        Object stock = redisTemplate.opsForValue().get(String.valueOf(productId));
        return Long.valueOf(stock == null ? "-1" : (String) stock);
    }
}
