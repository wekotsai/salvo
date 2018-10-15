package com.codeoftheweb.salvo;
//import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PlayerRepository extends JpaRepository<Player, Long> {
//    List<Player> findByLastName(String lastName);
}
