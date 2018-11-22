package com.codeoftheweb.salvo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface ShipRepository extends JpaRepository<Ship, Long> {
    Ship findById(@Param("nn")long nn);
}
