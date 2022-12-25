package com.games.tictactoe.controller;


import com.games.tictactoe.controller.dto.ConnectRequest;
import com.games.tictactoe.exception.InvalidGameException;
import com.games.tictactoe.exception.InvalidParamException;
import com.games.tictactoe.exception.NotFoundException;
import com.games.tictactoe.model.Game;
import com.games.tictactoe.model.GamePlay;
import com.games.tictactoe.model.Player;
import com.games.tictactoe.service.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/game")
public class GameController {

@Autowired
    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody Player player){
        log.info("start game request: {}",player);
        return ResponseEntity.ok(gameService.createGame(player));
    }

    @PostMapping("/connect")
    public ResponseEntity<Game> connect(@RequestBody ConnectRequest request ) throws InvalidParamException, InvalidGameException {
        log.info("connect request:{}",request);
        return ResponseEntity.ok(gameService.connectToGame(request.getPlayer(),request.getGameId()));

    }


    @PostMapping("/connect/random")
    public ResponseEntity<Game> connectRandom(@RequestBody Player player) throws NotFoundException {
        log.info("connect random : {}",player);
        return ResponseEntity.ok(gameService.connectToRandomGame(player));
    }


    @PostMapping("/gameplay")
    public ResponseEntity<Game> gamePlay(@RequestBody GamePlay request) throws InvalidGameException, NotFoundException {
        log.info("gameplay request: {}", request);
        Game game = gameService.gamePlay(request);
        simpMessagingTemplate.convertAndSend("/topic/game-progress/" + game.getGameId(), game);
        return ResponseEntity.ok(game);

    }

}
