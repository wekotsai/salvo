package com.codeoftheweb.salvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GamePlayerRepository gameplayerrepo;
    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/gameplayers")
    public List<GamePlayer> getAll() {
        return gameplayerrepo.findAll();
    }

//    @GetMapping("/players")
//    public List<Player> getPlayers() {
//        return playerRepository.findAll();
//    }

    @GetMapping("/games")
    public List<Object> getGames() {
       return gameRepository.findAll().stream().map(game -> gameMap(game)).collect(toList());
    }

    @GetMapping("/game_view/{nn}")
    public Object getGameView(@PathVariable Long nn) {
        return gameRepository.findById(nn);
    }

//    @GetMapping("/game_view/{nn}")
//    public Map<String, Object> getGameView(@PathVariable long nn) {
//        GamePlayer currentGP = gameplayerrepo.findById(nn);
//        return getGameView(nn);
//    }

    private Map<String, Object> gameMap(Game game) {
        Map<String, Object> gamemap = new LinkedHashMap<String, Object>();
        gamemap.put("id", game.getId());
        gamemap.put("created", game.getDate());
        gamemap.put("gamePlayer", gameplayerSet(game.gamePlayers));
        gamemap.put("ships", game.gamePlayers.stream().map(gamePlayer -> gamePlayer.getShips()).collect(toList()));
        return gamemap;
    }

    private Map<String, Object> playerMap(Player player) {
        Map<String, Object> playermap = new LinkedHashMap<String, Object>();
        playermap.put("id", player.getId());
        playermap.put("email", player.getEmail());
        return playermap;
    }

    private Map<String, Object> gameplayerMap(GamePlayer gamePlayer) {
        Map<String, Object> gameplayermap = new LinkedHashMap<String, Object>();
        gameplayermap.put("id", gamePlayer.getId());
        gameplayermap.put("player", playerMap(gamePlayer.getPlayer()));
        return gameplayermap;
    }

    private Map<String, Object> shipMap(Ship ship) {
        Map<String, Object> shipmap = new LinkedHashMap<String, Object>();
        shipmap.put("type", ship.getType());
        shipmap.put("location", ship.getLocation());
        return shipmap;
    }

    private List<Map<String, Object>> gameplayerSet (Set<GamePlayer> gamePlayerSet) {
        return gamePlayerSet.stream().map(gameplayer -> gameplayerMap(gameplayer)).collect(Collectors.toList());
    }

}
