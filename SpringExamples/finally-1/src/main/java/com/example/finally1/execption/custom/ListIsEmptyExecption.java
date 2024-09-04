package com.example.finally1.execption.custom;

public class ListIsEmptyExecption extends RuntimeException{
    public ListIsEmptyExecption(String message){
        super(message);
    }
}
