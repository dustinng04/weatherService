package com.example.weatherService.exception;

public class InvalidLocationKeyException extends RuntimeException {
	 public InvalidLocationKeyException(String message) {
        super(message);
    }
}
