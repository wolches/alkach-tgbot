package io.github.wolches.tgbot.alkach.persistance;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatShippering;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Comparator;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShipperingDao {

    @PersistenceContext
    private final EntityManager entityManager;

    //TODO

    public Optional<ChatShippering> findLastChatShippering(Chat chat) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatShippering> criteriaQuery = criteriaBuilder.createQuery(ChatShippering.class);

        Root<ChatShippering> shippering = criteriaQuery.from(ChatShippering.class);
        Predicate shipperingByChat = criteriaBuilder.equal(shippering.get("chat"), chat);
        criteriaQuery.where(shipperingByChat);

        TypedQuery<ChatShippering> query = entityManager.createQuery(criteriaQuery);
        Optional<ChatShippering> last = query
                .getResultList()
                .stream()
                .max(Comparator.comparing(ChatShippering::getShipperedAt));

        return last;
    }
}
