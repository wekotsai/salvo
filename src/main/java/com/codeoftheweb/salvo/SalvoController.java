package com.codeoftheweb.salvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @Autowired
    private ShipRepository shipRepository;

    private Player getCurrentUser(Authentication authentication) {
        System.out.println("Current: " + authentication.getName());
        return playerRepository.findByEmail(authentication.getName());
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    @RequestMapping(value = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> createGame(Authentication authentication) {
        Map<String, Object> createAGame = new LinkedHashMap<>();
        if (authentication == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createAGame);
        } else {
            Game game = new Game();
            gameRepository.save(game);
            GamePlayer gamePlayer = new GamePlayer(game, getCurrentUser(authentication));
            gameplayerrepo.save(gamePlayer);
            return new ResponseEntity<>(Map("gpid", String.valueOf(gamePlayer.getId())), HttpStatus.CREATED);
        }
    }

    @RequestMapping("/games")
    public Map<String,Object> getGames(Game game, Authentication authentication){
        Map<String, Object> newGames = new LinkedHashMap<>();
        if (authentication == null){
            newGames.put("Current", null);
            newGames.put("games", new ArrayList<>());
        } else {
            newGames.put("current", getCurrentUser(authentication));
            newGames.put("games", gameRepository.findAll().stream().map(games -> gameMap(games)).collect(toList()));
        }
        return newGames;
    }

    @RequestMapping("/players")
    public ResponseEntity<Map<String, Object>> getNewPlayer(@RequestParam String email, String password){
        if (playerRepository.findByEmail(email) == null){
            Player player = new Player(email, password);
            playerRepository.save(player);
            Map<String, Object> responseMap = new LinkedHashMap<String, Object>(){{
                put("New Player", player.getEmail());
            }};
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
        }else{
            Map<String, Object> newPlayer = new LinkedHashMap<String, Object>(){{
                put("error", "Name in use");
            }};
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(newPlayer);
        }
    }

    @GetMapping("/gameplayers")
    public List<GamePlayer> getAll() {
        return gameplayerrepo.findAll();
    }

    @GetMapping("/salvoes")
    public List<Object> getSalvoes() {
        return salvoRepository.findAll().stream().map(salvo -> salvoMap(salvo)).collect(toList());
    }

    @RequestMapping("/leaderboard")
    public List<Object> getPlayer() {
        return playerRepository.findAll().stream().map(player -> playerMap(player)).collect(toList());
    }

    @RequestMapping(value = "/games/players/{gpid}/ships", method = RequestMethod.POST)
    public HttpStatus getShips(@PathVariable long gpid, Authentication authentication, @RequestBody List<Ship> ships) {
        System.out.println(ships);

        GamePlayer gamePlayer = gameplayerrepo.findById(gpid);
        ships.stream().forEach(ship -> {
            ship.setGamePlayer(gamePlayer);
            shipRepository.save(ship);
        });
        if (authentication == null || gamePlayer == null || gamePlayer.getPlayer() != getCurrentUser(authentication)) {
            return HttpStatus.UNAUTHORIZED;
        } else if (gamePlayer.getShips() != null){
            return HttpStatus.FORBIDDEN;
        } else {
            ships.forEach(ship -> gamePlayer.addShip(ship));
            ships.forEach(ship -> shipRepository.save(ship));
            return HttpStatus.CREATED;
        }
    }

    @GetMapping("/game_view/{nn}")
    public ResponseEntity<Map<String, Object>> getGameView(@PathVariable long nn, Authentication authentication) {
        Map<String, Object> newId = new LinkedHashMap<>();
        GamePlayer gamePlayer = gameplayerrepo.findById(nn);
        if (isGuest(authentication)){
            return new ResponseEntity<>(Map("Error", "Player is not logged in"), HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(gamePMap(gamePlayer), HttpStatus.OK);
        }
    }

    @RequestMapping("/game/{id}/players")
    public ResponseEntity<Map<String, Object>> joinAGame(@PathVariable long id, Authentication authentication) {
        System.out.println(id);
        Map<String, Object> joinGame = new LinkedHashMap<>();
        Player currentUser = getCurrentUser(authentication);
        if (currentUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(joinGame);
        } else {
            Game game = gameRepository.findById(id);
            if (game == null) {
                return new ResponseEntity<>(Map("Error", "No such game"), HttpStatus.FORBIDDEN);
            } else {
                if (game.getGamePlayers().size() >= 2) {
                    return new ResponseEntity<>(Map("Error", "Game is full"), HttpStatus.FORBIDDEN);
                } else {
                    GamePlayer gamePlayer = new GamePlayer(game, currentUser);
                    gameplayerrepo.save(gamePlayer);
                    System.out.println(gamePlayer.getId());
                    return new ResponseEntity<>(Map("gpid", String.valueOf(gamePlayer.getId())), HttpStatus.CREATED);
                }
            }
        }
    }

    public Map<String, Object> Map(String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    private Map<String, Object> gameMap(Game game){
        Map<String, Object> gamemap = new LinkedHashMap<>();
        gamemap.put("gameid", game.getId());
        gamemap.put("created", game.getDate());
        gamemap.put("players", gameplayerSet(game.getGamePlayers()));
        return gamemap;
    }

    private LinkedHashMap<String, Object> getGameListMap(Game game){
        return new LinkedHashMap<String, Object>(){{
            put("id", game.getId());
        }};
    }

    private Map<String, Object> gamePMap(GamePlayer gameplayer) {
        Map<String, Object> gamepmap = new LinkedHashMap<String, Object>();
        gamepmap.put("gp_id", gameplayer.getId());
        gamepmap.put("created", gameplayer.getDate());
        gamepmap.put("gamePlayer", gameplayerSet(gameplayer.getGame().gamePlayers));
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

    private List<Map<String, Object>> playerSet(Set<Player> player) {
        return player.stream().map(player1 -> playerMap(player1)).collect(toList());
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