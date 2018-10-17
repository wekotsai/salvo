package com.codeoftheweb.salvo;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;
//import java.sql.Date;

@Entity
public class Game {

    public long getDate() {
        return new Date().getTime();
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    private ZonedDateTime date = ZonedDateTime.now();
    // private date = new Date();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayer;


    public Game() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
