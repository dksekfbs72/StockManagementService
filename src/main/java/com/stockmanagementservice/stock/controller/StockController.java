package com.stockmanagementservice.stock.controller;

import com.stockmanagementservice.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;
    @GetMapping
    public Long getStock(@RequestParam Long productId) {
        return stockService.getStock(String.valueOf(productId));
    }

    @PutMapping("/addStock")
    public String addStock(
            @RequestParam Long productId,
            @RequestParam Long amount
    ) {
        return stockService.addStock(productId, amount);
    }

    @PutMapping("/order")
    public String order(@RequestParam Long productId) {
        return stockService.order(String.valueOf(productId));
    }

    @PutMapping("/orderFail")
    public String orderFail(@RequestParam Long productId) {
        return stockService.orderFail(String.valueOf(productId));
    }
}
