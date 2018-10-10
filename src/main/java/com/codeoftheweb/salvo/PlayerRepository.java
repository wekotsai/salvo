package com.codeoftheweb.salvo;

//import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RepositoryRestResource
public interface PlayerRepository extends JpaRepository<Player, Long> {

}

@RestController
public class AppController {

    @Autowired
    private PlayerRepository PlayerRepository;
}

//public interface PlayerRepository extends JpaRepository<Player, Long> {
//    List<Player> findByLastName(String lastName);
//}
