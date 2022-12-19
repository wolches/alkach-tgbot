package io.github.wolches.tgbot.alkach.persistance;

import io.github.wolches.tgbot.alkach.domain.model.chat.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ship.ChatShippering;
import io.github.wolches.tgbot.alkach.domain.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.domain.model.ship.ChatUserShippering;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(Transactional.TxType.REQUIRED)
public class ShipperingDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public Optional<ChatShippering> findLastChatShippering(Chat chat) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatShippering> criteriaQuery = criteriaBuilder.createQuery(ChatShippering.class);

        Root<ChatShippering> shippering = criteriaQuery.from(ChatShippering.class);
        Predicate shipperingByChat = criteriaBuilder.equal(shippering.get("chat"), chat);
        criteriaQuery.where(shipperingByChat);

        return entityManager
                .createQuery(criteriaQuery)
                .getResultList()
                .stream()
                .max(Comparator.comparing(ChatShippering::getShipperedAt));
    }

    public List<ChatShippering> findChatShippering(Chat chat) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatShippering> criteriaQuery = criteriaBuilder.createQuery(ChatShippering.class);

        Root<ChatShippering> shippering = criteriaQuery.from(ChatShippering.class);
        Predicate shipperingByChat = criteriaBuilder.equal(shippering.get("chat"), chat);
        criteriaQuery.where(shipperingByChat);

        return entityManager
                .createQuery(criteriaQuery)
                .getResultList();
    }

    public List<ChatShippering> findChatShipperingByChatUser(ChatUser chatUser) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatShippering> criteriaQuery = criteriaBuilder.createQuery(ChatShippering.class);

        Root<ChatShippering> shippering = criteriaQuery.from(ChatShippering.class);
        Predicate shipperingByA = criteriaBuilder.equal(shippering.get("shipperedA"), chatUser);
        Predicate shipperingByB = criteriaBuilder.equal(shippering.get("shipperedB"), chatUser);
        Predicate shipperingByUser = criteriaBuilder.or(shipperingByA, shipperingByB);
        criteriaQuery.where(shipperingByUser);

        TypedQuery<ChatShippering> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public ChatShippering saveChatShippering(ChatShippering chatShippering) {
        entityManager.persist(chatShippering);
        return chatShippering;
    }

    public Optional<ChatUserShippering> findChatUserShippering(ChatUser chatUser) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatUserShippering> criteriaQuery = criteriaBuilder.createQuery(ChatUserShippering.class);

        Root<ChatUserShippering> shippering = criteriaQuery.from(ChatUserShippering.class);
        Predicate shipperingByChat = criteriaBuilder.equal(shippering.get("chatUser"), chatUser);
        criteriaQuery.where(shipperingByChat);

        TypedQuery<ChatUserShippering> query = entityManager.createQuery(criteriaQuery);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public ChatUserShippering saveChatUserShippering(ChatUserShippering chatUserShippering) {
        entityManager.persist(chatUserShippering);
        return chatUserShippering;
    }
}
