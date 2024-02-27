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
        return stockService.getStock(productId);
    }

    @PutMapping("/addStock")
    public Long addStock(
            @RequestParam Long productId
    ) {
        return stockService.changeStock(productId);
    }

    @PutMapping("/order")
    public String order(@RequestParam Long productId) {
        return stockService.order(productId);
    }

    @PutMapping("/orderFail")
    public String orderFail(@RequestParam Long productId) {
        return stockService.orderFail(String.valueOf(productId));
    }
}
