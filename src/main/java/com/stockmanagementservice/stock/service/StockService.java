package com.stockmanagementservice.stock.service;

import com.stockmanagementservice.global.config.feign.ProductClient;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final RedissonClient redissonClient;
    private final ProductClient productClient;

    @Transactional
    public Long findStock(Long productId) {
        RLock lock = redissonClient.getLock("stockLock");

        try {
            String key = String.valueOf(productId);
            lock.lock();

            long stock = redissonClient.getAtomicLong(key).get();

            if (stock == 0) {
                redissonClient.getAtomicLong(key).set(productClient.getStock(productId));
            }

            return redissonClient.getAtomicLong(key).get();
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public Long getStock(Long productId) {
        RLock lock = redissonClient.getLock("myLock");

        try {
            lock.lock();

            // Redis에서 상품의 재고량 조회
            Long stock = redissonClient.getAtomicLong(String.valueOf(productId)).get();
            if (stock == 0) {
                stock = findStock(productId);
            }

            // Redis에서 상품의 주문된 수량 조회
            long productOrder = redissonClient.getAtomicLong(productId + "Order").get();

            // 실제 재고량 계산
            return stock - productOrder;
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public String order(Long productId) {
        RLock lock = redissonClient.getLock("myLock");

        try {
            lock.lock();

            Long stock = redissonClient.getAtomicLong(String.valueOf(productId)).get();
            if (stock == 0) {
                stock = findStock(productId);
            }

            // Redis에서 상품의 주문된 수량 조회
            long productOrder = redissonClient.getAtomicLong(productId + "Order").get();

            // 실제 재고량 계산
            if (stock - productOrder <= 0) {
                return "재고 부족";
            }

            redissonClient.getAtomicLong(productId + "Order").incrementAndGet();
            return "주문 생성";
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public String orderFail(String productId) {
        RLock lock = redissonClient.getLock("myLock");

        try {
            lock.lock();

            long orderCount = redissonClient.getAtomicLong(productId + "Order").get();

            if (orderCount == 0) {
                return "주문 실패";
            }

            // 주문된 수량을 감소시킴
            redissonClient.getAtomicLong(productId + "Order").decrementAndGet();

            return "주문 실패";
        } finally {
            lock.unlock();
        }
    }

    public Long changeStock(Long productId) {
        RLock lock = redissonClient.getLock("stockLock");

        try {
            String key = String.valueOf(productId);

            lock.lock();

            redissonClient.getAtomicLong(key).set(productClient.getStock(productId));

            return redissonClient.getAtomicLong(key).get();
        } finally {
            lock.unlock();
        }
    }
}
