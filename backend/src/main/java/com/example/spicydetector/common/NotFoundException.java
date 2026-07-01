package com.example.spicydetector.common;

/**
 * 요청한 리소스를 찾을 수 없을 때 던지는 예외. (HTTP 404 로 변환)
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
