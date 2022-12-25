package com.games.tictactoe.exception;

public class InvalidGameException extends Exception{
    private String message;

    public InvalidGameException(String msg){
        this.message=msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

