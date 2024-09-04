package com.example.finally1.execption.custom;

public class FileNotFound extends RuntimeException{
    public FileNotFound(String message){
        super(message);
    }
}
