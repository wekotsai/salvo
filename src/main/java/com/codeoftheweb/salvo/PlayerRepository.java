package com.codeoftheweb.salvo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByEmail(String email);
}
