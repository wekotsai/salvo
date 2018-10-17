package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
			Player player1 = new Player ("Jack Bauer", "j.bauer@ctu.gov");
			Player player2 = new Player ("Chloe O'Brian", "c.obrian@ctu.gov");
			Player player3 = new Player ("Kim Bauer", "kim_bauer@gmail.gov");
			Player player4 = new Player ("Tony Almeida", "t.almeida@ctu.gov");

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

			GamePlayer gamePlayer1 = new GamePlayer(game1, player1);
			GamePlayer gamePlayer2 = new GamePlayer(game1, player2);

			gameplayerrepository.save(gamePlayer1);
			gameplayerrepository.save(gamePlayer2);

			List<String> loc1 = Arrays.asList("D3", "E3", "F3");
			List<String> loc2 = Arrays.asList("B5","B6","B7","B8","B9");
			List<String> loc3 = Arrays.asList("I1","I2","I3");
			List<String> loc4 = Arrays.asList("E8","F8","G8","H8");
			List<String> loc5 = Arrays.asList("G5","G6");

			Ship ship1 = new Ship("destroyer", loc1);
			Ship ship2 = new Ship("carrier", loc2);
			Ship ship3 = new Ship("submarine",loc3);
			Ship ship4 = new Ship("battleship",loc4);
			Ship ship5 = new Ship("patrolBoat", loc5);

			shiprepository.save(ship1);
			shiprepository.save(ship2);
			shiprepository.save(ship3);
			shiprepository.save(ship4);
			shiprepository.save(ship5);


		};
	}
}
