package com.games.tictactoe.service;

import com.games.tictactoe.exception.InvalidGameException;
import com.games.tictactoe.exception.InvalidParamException;
import com.games.tictactoe.exception.NotFoundException;
import com.games.tictactoe.model.Game;
import com.games.tictactoe.model.GamePlay;
import com.games.tictactoe.model.Player;

public interface GameService {
    Game createGame(Player player);
    Game connectToGame(Player player,String gameId) throws InvalidParamException, InvalidGameException;
    Game connectToRandomGame(Player player2) throws NotFoundException;
    Game gamePlay(GamePlay gamePlay) throws NotFoundException, InvalidGameException;
}
