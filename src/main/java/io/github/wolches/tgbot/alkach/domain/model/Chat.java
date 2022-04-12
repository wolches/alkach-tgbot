package io.github.wolches.tgbot.alkach.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "chats",
        indexes = {
                @Index(name = "chat_tg_id_idx", columnList = "telegram_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "chat_tg_id_uq", columnNames = "telegram_id")
        }
)
public class Chat {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "telegram_id")
    private Long telegramId;

    @Column(name = "msg_count")
    private Long messageCount;

    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    private List<ChatUser> chatUsers;

    @Column(name = "chat_name")
    private String chatName;

    public void incrementMessageCount() {
        messageCount++;
    }

    public static Chat createNew(long telegramId) {
        return Chat.builder()
                .telegramId(telegramId)
                .messageCount(0L)
                .chatUsers(new ArrayList<>())
                .build();
    }
}
