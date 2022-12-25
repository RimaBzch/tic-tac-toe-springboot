package com.games.tictactoe.exception;

public class InvalidParamException extends Exception{
    private String message;

    public InvalidParamException(String msg){
        this.message=msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
