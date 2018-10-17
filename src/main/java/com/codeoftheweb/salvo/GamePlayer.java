package com.codeoftheweb.salvo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
public class GamePlayer {

    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    Set<Ship> ship;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public GamePlayer() { }

    public GamePlayer(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.date = new Date();
    }

    public Set<Ship> getShip() {
        return getShip();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}