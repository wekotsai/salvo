package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerrepository,
									  GameRepository gamerepository,
									  GamePlayerRepository gameplayerrepository) {
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

			gameplayerrepository.save(gamePlayer1);

//			repository.save(new Player("Jack", "Bauer"));
//			repository.save(new Player("Chloe", "O'Brian"));
//			repository.save(new Player("Kim", "Bauer"));
//			repository.save(new Player("Tony", "Almeida"));
		};
	}
}
