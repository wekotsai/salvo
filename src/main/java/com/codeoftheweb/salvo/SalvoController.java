package com.codeoftheweb.salvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
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
    @Autowired
    private SalvoRepository salvoRepository;

    @GetMapping("/games")
    public List<Object> getGames(){
        return gameplayerrepo
                .findAll()
                .stream()
                .map(game -> gameMap(game))
                .collect(toList());
    }

    @GetMapping("/gameplayers")
    public List<GamePlayer> getAll() {
        return gameplayerrepo.findAll();
    }

    @GetMapping("/salvoes")
    public List<Object> getSalvoes() {
        return salvoRepository.findAll().stream().map(salvo -> salvoMap(salvo)).collect(toList());
    }

    @GetMapping("/game_view/{nn}")
    public Map<String, Object> getGameView(@PathVariable Long nn) {
        return gamePMap(gameplayerrepo.findById(nn).get());
    }

    private Map<String, Object> gameMap(GamePlayer gamePlayer){
        Map<String, Object> gamemap = new LinkedHashMap<>();
        gamemap.put("player", playerMap(gamePlayer.getPlayer()));
        return gamemap;
    }

    private Map<String, Object> gamePMap(GamePlayer gameplayer) {
        Map<String, Object> gamepmap = new LinkedHashMap<String, Object>();
        gamepmap.put("gp_id", gameplayer.getId());
        gamepmap.put("created", gameplayer.getDate());
        gamepmap.put("gamePlayer", gameplayerSet(gameplayer.getGames().gamePlayers));
        gamepmap.put("ships", shipSet(gameplayer.getShips()));
        gamepmap.put("salvoes", salvoSet(gameplayer.getSalvoes()));
        gamepmap.put("hitTheOpp", hitTheOpp(gameplayer));
        gamepmap.put("opponentsSalvoes", salvoSet(gameplayer.getOpponentsSalvoes(gameplayer)));
        return gamepmap;
    }

    private Map<String, Object> playerMap(Player player) {
        Map<String, Object> playermap = new LinkedHashMap<String, Object>();
        playermap.put("player_id", player.getId());
        playermap.put("email", player.getEmail());
        playermap.put("player_score", scoreSet(player.getScores()));
        return playermap;
    }

    private Map<String, Object> gameplayerMap(GamePlayer gamePlayer) {
        Map<String, Object> gameplayermap = new LinkedHashMap<String, Object>();
        gameplayermap.put("gp_id", gamePlayer.getId());
        gameplayermap.put("player", playerMap(gamePlayer.getPlayer()));
        return gameplayermap;
    }

    private Map<String, Object> shipMap(Ship ship) {
        Map<String, Object> shipmap = new LinkedHashMap<String, Object>();
        shipmap.put("type", ship.getType());
        shipmap.put("location", ship.getLocation());
        return shipmap;
    }

    private Map<String, Object> salvoMap(Salvo salvo) {
        Map<String, Object> salvomap = new LinkedHashMap<String, Object>();
        salvomap.put("turn", salvo.getTurn());
        salvomap.put("location", salvo.getLocation());
        salvomap.put("player_id", salvo.getGamePlayer().getPlayer().getId());
        return salvomap;
    }

    private Map<String, Object> scoreMap(Score score) {
        Map<String, Object> scoremap = new LinkedHashMap<String, Object>();
//        scoremap.put("player", score.getPlayer());
        scoremap.put("finishDate", score.getFinishDate());
        scoremap.put("game_score", score.getGame().getScore(score.getPlayer()));
        return scoremap;
    }

    private List<Map<String, Object>> scoreSet (Set<Score> score){
        return score
                .stream()
                .map(scores -> scoreMap(scores))
                .collect(toList());
    }

    private List<Map<String, Object>> gameplayerSet (Set<GamePlayer> gamePlayerSet) {
        return gamePlayerSet.stream().map(gameplayer -> gameplayerMap(gameplayer)).collect(toList());
    }

    private List<Map<String, Object>> shipSet (Set<Ship> shipSet) {
        return shipSet.stream().map(ship -> shipMap(ship)).collect(toList());
    }

    private List<Map<String, Object>> salvoSet (Set<Salvo> salvoSet) {
        return salvoSet.stream().map(salvo -> salvoMap(salvo)).collect(toList());
    }

    public List<String> hitTheOpp(GamePlayer gamePlayer){
        List<String> hitTheOpp = new ArrayList<>();
        for (String shipLoc: gamePlayer.oppoShipList(gamePlayer)){
            for (String salvoLoc: gamePlayer.salvoList(gamePlayer)){
                if (shipLoc == salvoLoc) {
                  if (!hitTheOpp.contains(shipLoc)) {
                      hitTheOpp.add(shipLoc);
                  }
                }
            }
        }
        return hitTheOpp;
    }


}
