package com.codeoftheweb.salvo;

import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.sql.Date;

@Entity
public class GamePlayer {

    @CreatedDate
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
    }

    public Player getGames() {
        return this.player;
    }

    public Game getPlayer() {
        return this.game;
    }

    public Date getDate() { return date; }

    public void setDate(Date date) {
        this.date = date;
    }
}
