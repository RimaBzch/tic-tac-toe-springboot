package com.games.tictactoe.controller.dto;

import com.games.tictactoe.model.Player;
import lombok.Data;

@Data
public class ConnectRequest {
    private  Player player;
    private  String gameId;


}
