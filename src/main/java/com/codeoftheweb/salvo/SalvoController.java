package com.codeoftheweb.salvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GamePlayerRepository gameplayerrepository;
    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping("/gameplayers")
    public List<GamePlayer> getAll() {
        return gameplayerrepository.findAll();
    }

    @RequestMapping("/players")
    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    @RequestMapping("/games")
    public List<Game> getGames() {
       return gameRepository.findAll();
    }
}
