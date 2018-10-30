package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Set;


@Entity
public class Player {

    private String email;
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    public Set<GamePlayer> getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(Set<GamePlayer> gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    @JsonIgnore
    Set<GamePlayer> gamePlayer;

    @OneToMany(mappedBy ="player", fetch=FetchType.EAGER)
    Set<Score> scores;

    public Player() { }

    public Score getOneScore(Game game){
        for (Score score: scores){
            if (score.getGame() == game){
                return score;
            }
        }
        return null;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public Player(String email, String password) {
        this.email = email;
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addPlayer(GamePlayer gamePlayer) {
        gamePlayer.setPlayer(this);
        Player.add(gamePlayer);
    }

    private static void add(GamePlayer gamePlayer) {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
