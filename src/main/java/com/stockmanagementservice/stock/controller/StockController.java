package com.stockmanagementservice.stock.controller;

import com.stockmanagementservice.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;
    @GetMapping
    public Long getStock(@RequestParam Long productId) {
        return stockService.getStock(productId);
    }

    @PutMapping("/addStock")
    public String addStock(
            @RequestParam Long productId,
            @RequestParam Long amount
    ) {
        return stockService.addStock(productId, amount);
    }
}
