package com.codeoftheweb.salvo;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Game {

    private ZonedDateTime date = ZonedDateTime.now();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<Score> scores;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    public Game() { }

    public Score getScore (Player player){
        return player.getOneScore(this);
    }

    public GamePlayer getOpponent(GamePlayer currentGamePlayer) {
        for (GamePlayer otherGamePlayer: this.gamePlayers) {
            if (currentGamePlayer.getId() != otherGamePlayer.getId()) {
             return otherGamePlayer;
            }
        }
        return null;
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
        return gamePlayers;
    }

    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayer.setGame(this);
        gamePlayers.add(gamePlayer);
    }
}