package com.codeoftheweb.salvo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface GamePlayerRepository extends JpaRepository<GamePlayer, Long>{
    GamePlayer findById(long id);
}
