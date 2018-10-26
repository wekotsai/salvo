package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {

    private double score;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "Player_id")
    private Player player;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "Game_id")
    private Game game;

    private Date finishDate;

    public Score() {}

    public Score(Player player, Game game, double score) {
        this.player = player;
        this.game = game;
        this.finishDate = new Date();
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public double getScores() {
        return score;
    }

    public void setScore(double score) {
        score = score;
    }

}
