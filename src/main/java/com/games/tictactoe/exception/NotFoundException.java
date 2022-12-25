package com.games.tictactoe.exception;

public class NotFoundException extends Exception{
    private String message;

    public NotFoundException(String msg){
        this.message=msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
