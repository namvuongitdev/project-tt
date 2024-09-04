package com.example.finally1.execption.custom;

public class UserNotFoundExection extends RuntimeException{
    public UserNotFoundExection(String message) {
        super(message);
    }
}
