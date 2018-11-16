package com.codeoftheweb.salvo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface GamePlayerRepository extends JpaRepository<GamePlayer, Long>{
    GamePlayer findById(@Param("nn")long nn);
}
