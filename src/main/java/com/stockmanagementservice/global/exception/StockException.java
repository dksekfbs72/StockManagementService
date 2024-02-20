package com.stockmanagementservice.global.exception;

import com.stockmanagementservice.global.type.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StockException extends RuntimeException{
    private ErrorCode errorCode;
    private String description;

    public StockException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.description = errorCode.getDescription();
    }
}
