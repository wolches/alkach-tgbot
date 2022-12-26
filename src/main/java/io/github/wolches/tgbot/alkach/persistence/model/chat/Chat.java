package io.github.wolches.tgbot.alkach.persistence.model.chat;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "telegramId"})
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

    @Column(name = "is_group")
    private boolean group;

    @Column(name = "is_supergroup")
    private boolean superGroup;

    @Column(name = "is_user")
    private boolean user;

    @Column(name = "is_channel")
    private boolean channel;

    public void incrementMessageCount() {
        messageCount++;
    }

    public List<ChatUser> getActiveChatUsers() {
        return chatUsers.stream()
                .filter(ChatUser::getActive)
                .collect(Collectors.toList());
    }

    public static Chat createNew(org.telegram.telegrambots.meta.api.objects.Chat tgChat) {
        return Chat.builder()
                .telegramId(tgChat.getId())
                .messageCount(0L)
                .chatUsers(new ArrayList<>())
                .group(tgChat.isGroupChat())
                .superGroup(tgChat.isGroupChat())
                .user(tgChat.isUserChat())
                .channel(tgChat.isChannelChat())
                .build();
    }
}
