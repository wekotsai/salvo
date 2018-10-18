package com.codeoftheweb.salvo;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
//import java.sql.Date;

@Entity
public class Game {

    private ZonedDateTime date = ZonedDateTime.now();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@JsonIgnore
    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayer;

    //private List<String> ship;

    public Game() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDate() {
        return new Date().getTime();
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Set<GamePlayer> getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(Set<GamePlayer> gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

//    public List<String> getShip() {
//        return ship;
//    }
//
//    public void setShip(List<String> ship) {
//        this.ship = ship;
//    }
}