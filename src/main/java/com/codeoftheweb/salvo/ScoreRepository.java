package com.codeoftheweb.salvo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ScoreRepository extends JpaRepository<Score, Long> {
}
