package com.games.tictactoe.service;

import com.games.tictactoe.exception.InvalidGameException;
import com.games.tictactoe.exception.InvalidParamException;
import com.games.tictactoe.exception.NotFoundException;
import com.games.tictactoe.model.*;
import com.games.tictactoe.storage.GameStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.games.tictactoe.model.GameStatus.*;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService{

    @Override
    public Game createGame(Player player) {
        Game game=new Game();
        game.setBoard(new int[3][3]);
        game.setGameId(UUID.randomUUID().toString());
        game.setPlayer1(player);
        game.setStatus(NEW);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    @Override
    public Game connectToGame(Player player2, String gameId) throws InvalidParamException, InvalidGameException {
        if(!GameStorage.getInstance().getGames().containsKey(gameId)){
            throw new InvalidParamException("Game with provided id doesn't exist!");
        }
        Game game=GameStorage.getInstance().getGames().get(gameId);

        if(game.getPlayer2()!=null){
            throw new InvalidGameException("Game is full");
        }

        game.setPlayer2(player2);
        game.setStatus(IN_PROGRESS);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    @Override
    public Game connectToRandomGame(Player player2) throws NotFoundException {
        Game game=GameStorage.getInstance().getGames().values().stream()
                .filter(el->el.getStatus().equals(NEW))
                .findFirst().orElseThrow(()-> new NotFoundException("Game not found!"));
        game.setPlayer2(player2);
        game.setStatus(IN_PROGRESS);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    @Override
    public Game gamePlay(GamePlay gamePlay) throws NotFoundException, InvalidGameException {
        if (!GameStorage.getInstance().getGames().containsKey(gamePlay.getGameId())){
            throw new NotFoundException("Game not found!");
        }
        Game game=GameStorage.getInstance().getGames().get(gamePlay.getGameId());
        if (game.getStatus().equals(FINISHED)){
            throw new InvalidGameException("Game is already finished!");
        }
        int [][] board =game.getBoard();
        board[gamePlay.getCoordinateX()][gamePlay.getCoordinateY()]= gamePlay.getType().getValue();

        Boolean xWinner =checkWinner(game.getBoard(), TicToe.X);
        Boolean oWinner =checkWinner(game.getBoard(), TicToe.O);

        if(xWinner){
            game.setWinner(TicToe.X);
        }else{
            game.setWinner(TicToe.O);
        }
        GameStorage.getInstance().setGame(game);
        return game;
    }

    private Boolean checkWinner(int[][] board, TicToe ticToe) {
        int[] boardArray= new int[9];
        int counterIndex=0;
        for (int i =0; i< board.length;i++){
            for (int j =0; j< board[i].length;j++){
                boardArray[counterIndex]=board[i][j];
                counterIndex++;
            }
        }
        int [][] winCombination={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
        for (int i =0; i< winCombination.length;i++){
            int counter=0;
            for (int j =0; j< winCombination[i].length;j++){
                if (boardArray[winCombination[i][j]]== ticToe.getValue()){
                    counter++;
                    if (counter==3){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

