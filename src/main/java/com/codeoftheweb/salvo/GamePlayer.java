package com.codeoftheweb.salvo;

import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.util.Date;

@Entity
public class GamePlayer {

    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public GamePlayer() { }

    public GamePlayer(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.date = new Date();
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
//
//    fetch('api/projects').then(response => {
//        response.json().then(json => {
//        let data = json;
//        });
//        });