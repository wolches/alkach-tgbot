package io.github.wolches.tgbot.alkach.domain.model.user;

import io.github.wolches.tgbot.alkach.domain.model.chat.ChatUser;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "telegramId"})
@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "user_tg_id_idx", columnList = "telegram_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "user_tg_id_uq", columnNames = "telegram_id")
        }
)
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "last_username")
    private String lastUsername;

    @Column(name = "last_usertag")
    private String lastUserTag;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Collection<ChatUser> userInChats;

    @Column(name = "telegram_id")
    private Long telegramId;

    @Column(name = "msg_count")
    private Long messageCount;

    @Column(name = "fucked_count")
    private Long fuckedCount;

    @Column(name = "fucker_count")
    private Long fuckerCount;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    UserSettings settings;

    public void incrementMessageCount() {
        messageCount++;
    }

    public void incrementFuckedCount() {
        fuckedCount++;
    }

    public void incrementFuckerCount() {
        fuckerCount++;
    }

    public static User createNew(long telegramId) {
        return User.builder()
                .telegramId(telegramId)
                .messageCount(0L)
                .fuckedCount(0L)
                .fuckerCount(0L)
                .userInChats(new ArrayList<>())
                .build();
    }
}
