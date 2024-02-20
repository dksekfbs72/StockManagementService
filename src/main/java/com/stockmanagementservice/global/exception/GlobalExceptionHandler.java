package com.stockmanagementservice.global.exception;

import com.stockmanagementservice.global.dto.WebResponseData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StockException.class)
    public WebResponseData<Object> userExceptionHandler(StockException stockException){
        return WebResponseData.error(stockException.getErrorCode());
    }
}
