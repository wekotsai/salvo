package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Set;


@Entity
public class Player {

    private String email;

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

    public Player() { }

    public Player(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void addPlayer(GamePlayer gamePlayer) {
        gamePlayer.setPlayer(this);
        Player.add(gamePlayer);
    }

    private static void add(GamePlayer gamePlayer) {
    }

}
