package com.codeoftheweb.salvo;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "Player_id")
    private Player player;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "Game_id")
    private Game game;

    private Date finishDate;

    public Score(Player player, Game game, Date finishDate, double score) {
        this.player = player;
        this.game = game;
        this.finishDate = finishDate;
        Score = score;
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

    public double getScore() {
        return Score;
    }

    public void setScore(double score) {
        Score = score;
    }

    private double Score;

    public Score() {}
}
