package io.github.wolches.tgbot.alkach.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "chat_users",
        indexes = {
                @Index(name = "chat_users_chat_id_idx", columnList = "chat_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "chat_user_uq", columnNames = {"chat_id", "user_id"})
        })
public class ChatUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "msg_count")
    private Long messageCount;

    @Column(name = "active")
    private Boolean active;

    public void incrementMessageCount() {
        messageCount++;
    }


    public static ChatUser createNew(Chat chat, User user) {
        return ChatUser.builder()
                .chat(chat)
                .user(user)
                .messageCount(0L)
                .build();
    }
}
