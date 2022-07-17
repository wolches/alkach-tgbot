package io.github.wolches.tgbot.alkach.persistance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class ShipperingDao {

    @PersistenceContext
    private final EntityManager entityManager;
}
