package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.*;


@Entity
public class GamePlayer {

    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    @JsonIgnore
    private Game game;

    public Set<Salvo> getSalvoes() {
        return salvoes;
    }

    public void setSalvoes(Set<Salvo> salvoes) {
        this.salvoes = salvoes;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public Set<Salvo> getOpponentsSalvoes(GamePlayer gamePlayer){
        return this.getGame().getOpponent(gamePlayer).getSalvoes();
    }

    public Set<Ship> getOpponentsShips(GamePlayer gamePlayer){
        return this.getGame().getOpponent(gamePlayer).getShips();
    }

    public List<String> oppoShipList(GamePlayer gamePlayer){
        List<String> oppoShipList = new ArrayList<>();
        for (Ship ship: getOpponentsShips(this)){
            for (String location: ship.getLocation()){
                oppoShipList.add(location);
            }
        }
        return oppoShipList;
    }

    public List<String> salvoList(GamePlayer gamePlayer){
        List<String> salvoList = new ArrayList<>();
        for (Salvo salvo: getSalvoes()){
            for (String location: salvo.getLocation()){
                salvoList.add(location);
            }
        }
        return salvoList;
    }


    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Salvo> salvoes = new HashSet<>();

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Ship> ships = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public GamePlayer() { }

    public GamePlayer(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.date = new Date();
    }

    public void addShip (Ship shipIn) {
        shipIn.setGamePlayer(this);
        ships.add(shipIn);
    }

    public void addSalvo (Salvo salvoIn) {
        salvoIn.setGamePlayer(this);
        salvoes.add(salvoIn);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonIgnore
    public Game getGames() {
        return this.game;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Date getDate() { return date; }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }
}