package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerrepository,
									  GameRepository gamerepository,
									  GamePlayerRepository gameplayerrepository,
									  ShipRepository shiprepository) {
		return (args) -> {
			Player player1 = new Player ( "j.bauer@ctu.gov");
			Player player2 = new Player ( "c.obrian@ctu.gov");
			Player player3 = new Player ("kim_bauer@gmail.gov");
			Player player4 = new Player ( "t.almeida@ctu.gov");

			playerrepository.save(player1);
			playerrepository.save(player2);
			playerrepository.save(player3);
			playerrepository.save(player4);

			Game game1 = new Game ();
			Game game2 = new Game ();
			Game game3 = new Game ();

			gamerepository.save(game1);
			gamerepository.save(game2);
			gamerepository.save(game3);

			GamePlayer gamePlayer1 = new GamePlayer( game1, player1);
			GamePlayer gamePlayer2 = new GamePlayer( game1, player2);
			gameplayerrepository.save(gamePlayer1);
			gameplayerrepository.save(gamePlayer2);

            GamePlayer gamePlayerA = new GamePlayer( game2, player1);
            GamePlayer gamePlayerB = new GamePlayer( game2, player2);
            gameplayerrepository.save(gamePlayerA);
            gameplayerrepository.save(gamePlayerB);

			List<String> loc1 = Arrays.asList("D3", "E3", "F3");
			List<String> loc2 = Arrays.asList("B5","B6","B7","B8","B9");
			List<String> loc3 = Arrays.asList("I1","I2","I3");
			List<String> loc4 = Arrays.asList("E8","F8","G8","H8");
			List<String> loc5 = Arrays.asList("G5","G6");
            List<String> loc6 = Arrays.asList("H2", "I2", "J2");
            List<String> loc7 = Arrays.asList("C1","C2","C3","C4","C5");
            List<String> loc8 = Arrays.asList("I5","I6","I7");
            List<String> loc9 = Arrays.asList("B8","B8","B8","B8");
            List<String> loc10 = Arrays.asList("F4","F5");

			// game 1
			Ship ship1 = new Ship("destroyer", game1);
			ship1.setLocation(loc1);
            shiprepository.save(ship1);
			Ship ship2 = new Ship("carrier", game1);
			ship2.setLocation(loc2);
            shiprepository.save(ship2);
			Ship ship3 = new Ship("submarine", game1);
			ship3.setLocation(loc3);
            shiprepository.save(ship3);
			Ship ship4 = new Ship("battleship", game1);
			ship4.setLocation(loc4);
            shiprepository.save(ship4);
			Ship ship5 = new Ship("patrolBoat", game1);
			ship5.setLocation(loc5);
            shiprepository.save(ship5);

            // game 2
            Ship shipA = new Ship("destroyer", game2);
            shipA.setLocation(loc6);
            shiprepository.save(shipA);
            Ship shipB = new Ship("carrier", game2);
            shipB.setLocation(loc7);
            shiprepository.save(shipB);
            Ship shipC = new Ship("submarine", game2);
            shipC.setLocation(loc8);
            shiprepository.save(shipC);
            Ship shipD = new Ship("battleship", game2);
            shipD.setLocation(loc9);
            shiprepository.save(shipD);
            Ship shipE = new Ship("patrolBoat", game2);
            shipE.setLocation(loc10);
            shiprepository.save(shipE);

		};
	}
}
