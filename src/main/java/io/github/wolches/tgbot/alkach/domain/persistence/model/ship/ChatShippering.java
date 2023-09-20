package io.github.wolches.tgbot.alkach.domain.persistence.model.ship;

import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.Chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_shippering")
public class ChatShippering {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "shippered_a_chat_user_id")
    private ChatUser shipperedA;

    @ManyToOne
    @JoinColumn(name = "shippered_b_chat_user_id")
    private ChatUser shipperedB;

    @Column(name = "shippered_at")
    private OffsetDateTime shipperedAt;
}
