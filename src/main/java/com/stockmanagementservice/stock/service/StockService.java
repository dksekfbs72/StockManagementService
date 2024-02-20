package com.stockmanagementservice.stock.service;

import com.stockmanagementservice.global.exception.StockException;
import com.stockmanagementservice.global.type.ErrorCode;
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

    public Long getStock(String productId) {
        Object stock = redisTemplate.opsForValue().get(productId);

        if (stock == null) throw new StockException(ErrorCode.NOT_FOUND_PRODUCT);

        Object productOrder = redisTemplate.opsForValue().get(productId+"Order");

        return Long.parseLong((String) stock) - (productOrder == null ? 0 : Long.parseLong((String) productOrder));
    }

    public String order(String productId) {
        Object orderCount = redisTemplate.opsForValue().get(productId+"Order");
        redisTemplate.opsForValue().set(productId +"Order",
                String.valueOf((orderCount == null ? 0 : Long.parseLong((String) orderCount)) + 1));
        return "주문 생성";
    }

    public String orderFail(String productId) {
        Object orderCount = redisTemplate.opsForValue().get(productId+"Order");

        if (orderCount == null || Long.parseLong((String) orderCount) == 0) {
            return "주문 실패";
        }

        redisTemplate.opsForValue().set(productId +"Order",
                String.valueOf(Long.parseLong((String) orderCount) - 1));
        return "주문 실패";
    }
}
