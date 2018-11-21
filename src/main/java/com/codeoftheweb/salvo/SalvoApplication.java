package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
									  ShipRepository shiprepository,
                                      SalvoRepository salvorepository,
                                      ScoreRepository scorerepository) {

		return (args) -> {
			Player player1 = new Player ( "j.bauer@ctu.gov", "24");
			Player player2 = new Player ( "c.obrian@ctu.gov", "42");
			Player player3 = new Player ("kim_bauer@gmail.gov", "kb");
			Player player4 = new Player ( "t.almeida@ctu.gov", "mole");
            Player player5 = new Player ( "a@ctu.gov", "123");
            Player player6 = new Player ( "b@ctu.gov", "456");
            Player player7 = new Player ( "c@ctu.gov", "789");
            Player player8 = new Player ( "d@ctu.gov", "000");

			playerrepository.save(player1);
			playerrepository.save(player2);
			playerrepository.save(player3);
			playerrepository.save(player4);
            playerrepository.save(player5);
            playerrepository.save(player6);
            playerrepository.save(player7);
            playerrepository.save(player8);

			Game game1 = new Game ();
			Game game2 = new Game ();
			Game game3 = new Game ();
            Game game4 = new Game ();

			gamerepository.save(game1);
			gamerepository.save(game2);
			gamerepository.save(game3);
            gamerepository.save(game4);

			GamePlayer gamePlayer1 = new GamePlayer( game1, player1);
			GamePlayer gamePlayer2 = new GamePlayer( game1, player2);
			gameplayerrepository.save(gamePlayer1);
			gameplayerrepository.save(gamePlayer2);

            GamePlayer gamePlayer3 = new GamePlayer( game2, player3);
            GamePlayer gamePlayer4 = new GamePlayer( game2, player4);
            gameplayerrepository.save(gamePlayer3);
            gameplayerrepository.save(gamePlayer4);

            GamePlayer gamePlayer5 = new GamePlayer( game3, player5);
            GamePlayer gamePlayer6 = new GamePlayer( game3, player6);
            gameplayerrepository.save(gamePlayer5);
            gameplayerrepository.save(gamePlayer6);

            GamePlayer gamePlayer7 = new GamePlayer( game4, player7);
            GamePlayer gamePlayer8 = new GamePlayer( game4, player8);
            gameplayerrepository.save(gamePlayer7);
            gameplayerrepository.save(gamePlayer8);

			List<String> loc1 = Arrays.asList("D3", "E3", "F3");
			List<String> loc2 = Arrays.asList("B5","B6","B7","B8","B9");
			List<String> loc3 = Arrays.asList("I1","I2","I3");
			List<String> loc4 = Arrays.asList("E8","F8","G8","H8");
			List<String> loc5 = Arrays.asList("G5","G6");

            List<String> loc6 = Arrays.asList("H2", "I2", "J2");
            List<String> loc7 = Arrays.asList("C1","C2","C3","C4","C5");
            List<String> loc8 = Arrays.asList("I5","I6","I7");
            List<String> loc9 = Arrays.asList("B8","C8","D8","E8");
            List<String> loc10 = Arrays.asList("F4","F5");

            List<String> locA = Arrays.asList("B8","C8","F1");
            List<String> locB = Arrays.asList("F4","D5", "E4");
            List<String> locC = Arrays.asList("I5", "B2", "E7");

            Salvo salvo1 = new Salvo(1, locA);
            gamePlayer1.addSalvo(salvo1);
            salvorepository.save(salvo1);
            Salvo salvo2 = new Salvo(1, locB);
            gamePlayer2.addSalvo(salvo2);
            salvorepository.save(salvo2);
            Salvo salvo3 = new Salvo(2, locC);
            gamePlayer1.addSalvo(salvo3);
            salvorepository.save(salvo3);

            Score score1 = new Score(player1, game1,0.5);
            Score score2 = new Score(player1, game2,0);
            Score score3 = new Score(player2, game3,1);
            Score score4 = new Score(player2, game4,0.5);
            Score score5 = new Score(player3, game2,1);
            Score score6 = new Score(player3, game3,1);
            Score score7 = new Score(player4, game1,0.5);
            Score score8 = new Score(player4, game1,1);

            gamePlayer1.addScore(score1);
            scorerepository.save(score1);
            gamePlayer1.addScore(score2);
            scorerepository.save(score2);
            gamePlayer2.addScore(score3);
            scorerepository.save(score3);
            gamePlayer2.addScore(score4);
            scorerepository.save(score4);
            gamePlayer3.addScore(score5);
            scorerepository.save(score5);


			Ship ship1 = new Ship("destroyer", loc1);
			gamePlayer1.addShip(ship1);
            shiprepository.save(ship1);
			Ship ship2 = new Ship("carrier", loc2);
            gamePlayer1.addShip(ship2);
            shiprepository.save(ship2);
			Ship ship3 = new Ship("submarine", loc3);
            gamePlayer1.addShip(ship3);
            shiprepository.save(ship3);
			Ship ship4 = new Ship("battleship", loc4);
            gamePlayer1.addShip(ship4);
            shiprepository.save(ship4);
			Ship ship5 = new Ship("patrolBoat", loc5);
            gamePlayer1.addShip(ship5);
            shiprepository.save(ship5);

            Ship shipA = new Ship("destroyer", loc6);
            gamePlayer2.addShip(shipA);
            shiprepository.save(shipA);
            Ship shipB = new Ship("carrier", loc7);
            gamePlayer2.addShip(shipB);
            shiprepository.save(shipB);
            Ship shipC = new Ship("submarine", loc8);
            gamePlayer2.addShip(shipC);
            shiprepository.save(shipC);
            Ship shipD = new Ship("battleship", loc9);
            gamePlayer2.addShip(shipD);
            shiprepository.save(shipD);
            Ship shipE = new Ship("patrolBoat", loc10);
            gamePlayer2.addShip(shipE);
            shiprepository.save(shipE);

		};
	}
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    PlayerRepository playerRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputName -> {
            System.out.println(inputName);
            Player player = playerRepository.findByEmail(inputName);
            System.out.println(player);
            if (player != null) {
                return User.withDefaultPasswordEncoder()
                        .username(player.getEmail())
                        .password(player.getPassword())
                        .roles("USER")
                        .build();
            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }
        });
    }
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/**").permitAll();
                .antMatchers("/web/games.html").permitAll()
                .antMatchers("/web/game.html").permitAll()
                .antMatchers("/web/games.js").permitAll()
                .antMatchers("/web/game.js").permitAll()
                .antMatchers("/web/style.css").permitAll()
                .antMatchers("/api/leaderboard").permitAll()
                .antMatchers("/web/games_style.css").permitAll()
                .antMatchers("/api/games").permitAll()
                .antMatchers("/api/players").permitAll()
                .antMatchers("/api/game_view/**").permitAll()
                .antMatchers("/**").hasAuthority("USER");

        http.formLogin()
                .usernameParameter("userName")
                .passwordParameter("password")
                .loginPage("/api/login");

//      http.()
//                .usernameParameter("userName")
//                .passwordParameter("password")
//                .loginPage("/api/signup");

        http.logout().logoutUrl("/api/logout");

        // turn off checking for CSRF tokens
        http.csrf().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}